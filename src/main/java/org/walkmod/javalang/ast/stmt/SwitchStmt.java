/*
 * Copyright (C) 2013 Raquel Pau and Albert Coroleu.
 * 
 * Walkmod is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * Walkmod is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Walkmod. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package org.walkmod.javalang.ast.stmt;

import java.util.LinkedList;
import java.util.List;

import org.walkmod.javalang.ast.Node;
import org.walkmod.javalang.ast.expr.Expression;
import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

/**
 * @author Julio Vilmar Gesser
 */
public final class SwitchStmt extends Statement {

    private Expression selector;

    private List<SwitchEntryStmt> entries;

    public SwitchStmt() {}

    public SwitchStmt(Expression selector, List<SwitchEntryStmt> entries) {
        setSelector(selector);
        setEntries(entries);
    }

    public SwitchStmt(int beginLine, int beginColumn, int endLine, int endColumn, Expression selector,
            List<SwitchEntryStmt> entries) {
        super(beginLine, beginColumn, endLine, endColumn);
        setSelector(selector);
        setEntries(entries);
    }

    @Override
    public boolean removeChild(Node child) {
        boolean result = false;

        if (child != null) {
            if (child == selector) {
                selector = null;
                result = true;
            }
            if (!result) {
                if (child instanceof SwitchStmt) {
                    if (entries != null) {
                        List<SwitchEntryStmt> entriesAux = new LinkedList<SwitchEntryStmt>(entries);
                        result = entriesAux.remove(child);
                        entries = entriesAux;
                    }
                }
            }
        }
        if (result) {
            updateReferences(child);
        }
        return result;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = super.getChildren();
        if (selector != null) {
            children.add(selector);
        }
        if (entries != null) {
            children.addAll(entries);
        }

        return children;
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        if (!check()) {
            return null;
        }
        return v.visit(this, arg);
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        if (check()) {
            v.visit(this, arg);
        }
    }

    public List<SwitchEntryStmt> getEntries() {
        return entries;
    }

    public Expression getSelector() {
        return selector;
    }

    public void setEntries(List<SwitchEntryStmt> entries) {
        this.entries = entries;
        setAsParentNodeOf(entries);
    }

    public void setSelector(Expression selector) {
        if (this.selector != null) {
            updateReferences(this.selector);
        }
        this.selector = selector;
        setAsParentNodeOf(selector);
    }

    @Override
    public boolean replaceChildNode(Node oldChild, Node newChild) {
        boolean updated = false;
        if (oldChild == selector) {
            setSelector((Expression) newChild);
            updated = true;
        }
        if (!updated && entries != null) {
            List<SwitchEntryStmt> auxEntries = new LinkedList<SwitchEntryStmt>(entries);

            updated = replaceChildNodeInList(oldChild, newChild, auxEntries);

            if (updated) {
                entries = auxEntries;
            }
        }

        return updated;
    }

    @Override
    public SwitchStmt clone() throws CloneNotSupportedException {
        return new SwitchStmt(clone(selector), clone(entries));
    }
}
