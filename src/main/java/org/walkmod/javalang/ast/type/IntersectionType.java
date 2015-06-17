/* 
 Copyright (C) 2013 Raquel Pau and Albert Coroleu.
 
 Walkmod is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 Walkmod is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public License
 along with Walkmod.  If not, see <http://www.gnu.org/licenses/>.*/
package org.walkmod.javalang.ast.type;

import java.util.List;

import org.walkmod.javalang.visitors.GenericVisitor;
import org.walkmod.javalang.visitors.VoidVisitor;

public class IntersectionType extends Type {

   private List<ReferenceType> bounds;

   public IntersectionType() {

   }

   public IntersectionType(List<ReferenceType> bounds) {
      setBounds(bounds);
   }

   public IntersectionType(int beginLine, int beginColumn, int endLine, int endColumn, List<ReferenceType> bounds) {
      super(beginLine, beginColumn, endLine, endColumn, null);
      setBounds(bounds);
   }

   @Override
   public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
      return v.visit(this, arg);
   }

   @Override
   public <A> void accept(VoidVisitor<A> v, A arg) {
      v.visit(this, arg);
   }

   public List<ReferenceType> getBounds() {
      return bounds;
   }

   public void setBounds(List<ReferenceType> bounds) {
      this.bounds = bounds;
      setAsParentNodeOf(bounds);
   }

}