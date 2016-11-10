// @(#)$Id: JMLRelationEnumerator.java-generic,v 1.41 2005/12/24 21:20:31 chalin Exp $

// Copyright (C) 2005 Iowa State University
//
// This file is part of the runtime library of the Java Modeling Language.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2.1,
// of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with JML; see the file LesserGPL.txt.  If not, write to the Free
// Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
// 02110-1301  USA.


package org.jmlspecs.models;

/** Enumerator for pairs of keys of type {@link _DomainType_} to
 * values of type {@link _RangeType_} that form the associations in a
 * relation.
 *
 * @version $Revision: 1.41 $
 * @author Gary T. Leavens
 * @see JMLEnumeration
 * @see JMLValueType
 * @see JMLObjectToEqualsRelationImageEnumerator
 * @see JMLObjectToEqualsRelation
 * @see JMLObjectToEqualsMap
 * @see JMLEnumerationToIterator
 * @see JMLValueSet
 */
// FIXME: adapt this file to non-null-by-default and remove the following modifier.
/*@ nullable_by_default @*/ 
public class JMLObjectToEqualsRelationEnumerator<K, V>
    implements JMLEnumeration<JMLObjectEqualsPair<K, V>>, JMLValueType {

    //@ public model JMLValueSet<JMLObjectEqualsPair<K, V>> uniteratedPairs;      in objectState;

    /** An enumerator for the image pairs in this relation.
     */
    protected /*@ non_null @*/
        JMLObjectToEqualsRelationImageEnumerator<K, V> imagePairEnum;
    //@                                                in uniteratedPairs;

    /** The current key for pairs being enumerated.
     */
    protected K key;
    //@                     in uniteratedPairs;

    /** An enumerator for the range elements that are related to the
     *  key that have not yet been returned.
     */
    protected /*@ non_null @*/ JMLEqualsSetEnumerator<V> imageEnum;
    //@                                                   in uniteratedPairs;


    /*@ protected represents uniteratedPairs <- abstractValue();
      @*/

    /*@ protected invariant moreElements
      @             <==> imageEnum.moreElements|| imagePairEnum.moreElements;
      @*/

    /*@ protected invariant moreElements ==> key != null;
      @*/

    //@ public invariant elementType == \type(JMLObjectEqualsPair<K, V>);
    //@ public invariant !returnsNull;

    /** Initialize this with the given relation.
     */
    /*@ normal_behavior
      @   requires rel != null;
      @   assignable uniteratedPairs;
      @   assignable moreElements, elementType, returnsNull, owner;
      @   ensures uniteratedPairs.equals(rel.theRelation);
      @    ensures owner == null;
      @*/
    JMLObjectToEqualsRelationEnumerator(/*@ non_null @*/
                                           JMLObjectToEqualsRelation<K, V> rel) {
        //@ set owner = null;
        //@ set elementType = \type(JMLObjectEqualsPair<K, V>);
        //@ set returnsNull = false;
        imagePairEnum = rel.imagePairs();
        if (imagePairEnum.hasMoreElements()) {
            JMLObjectValuePair<K, JMLEqualsSet<V>> p = imagePairEnum.nextImagePair();
            key = p.key;
            //@ assume key != null;
            //@ assume p.value != null;
            //@ assume p.value instanceof JMLEqualsSet<V>;
            imageEnum = p.value.elements();
            //@ assume imageEnum.moreElements;
        } else {
            key = null;
            imageEnum = (new JMLEqualsSet<V>()).elements();
            //@ assume !imageEnum.moreElements;
        }
    }

    //@ requires ipEnum != null;
    //@ requires iEnum != null;
    //@ requires (ipEnum.moreElements || iEnum.moreElements) ==> k != null;
    protected
        JMLObjectToEqualsRelationEnumerator(JMLObjectToEqualsRelationImageEnumerator<K, V> ipEnum,
                                               JMLEqualsSetEnumerator<V> iEnum,
                                               K k) {
        //@ assume (ipEnum.moreElements || iEnum.moreElements) ==> k != null;
        //@ set owner = null;
        //@ set elementType = \type(JMLObjectEqualsPair<K, V>);
        //@ set returnsNull = false;
        imagePairEnum
            = (JMLObjectToEqualsRelationImageEnumerator<K, V>)ipEnum.clone();
        imageEnum = (JMLEqualsSetEnumerator<V>)iEnum.clone();
        key = k;
        //@ assume moreElements ==> key != null;
    }

    /** Tells whether this enumerator has more uniterated elements.
     *  @see #nextElement
     *  @see #nextPair
     */
    /*@ also
      @  public normal_behavior
      @    assignable \nothing;
      @    ensures \result == !uniteratedPairs.isEmpty();
      @*/
    public /*@ pure @*/ boolean hasMoreElements() {
        return (imagePairEnum.hasMoreElements() || imageEnum.hasMoreElements());
    }

    /** Return the next image pair in this, if there is one.
     *  @exception JMLNoSuchElementException when this is empty.
     *  @see #hasMoreElements
     *  @see #nextPair
     */
    /*@ also
      @  public normal_behavior
      @    requires !uniteratedPairs.isEmpty();
      @    assignable uniteratedPairs;
      @    ensures \old(uniteratedPairs).has((JMLType)\result)
      @         && uniteratedPairs.
      @                equals(\old(uniteratedPairs).remove((JMLType)\result));
      @ also
      @  public exceptional_behavior
      @    requires uniteratedPairs.isEmpty();
      @    assignable \nothing;
      @    signals_only JMLNoSuchElementException;
      @*/
    public JMLObjectEqualsPair<K, V> nextElement() throws JMLNoSuchElementException {
        if (imageEnum.hasMoreElements()) {
            V v = imageEnum.nextElement();
            //@ assume v != null && v instanceof _RangeType_;
            return new JMLObjectEqualsPair<K, V>(key, v);
        } else if (imagePairEnum.hasMoreElements()) {
            JMLObjectValuePair<K, JMLEqualsSet<V>> imagePair = imagePairEnum.nextElement();
            //@ assume imagePair != null;
            key = imagePair.key;
            //@ assume imagePair.value != null;
            imageEnum = imagePair.value.elements();
            //@ assume imageEnum.moreElements;
            V v = imageEnum.nextElement();
            //@ assume v != null;
            return new JMLObjectEqualsPair<K, V>(key, v);
        } else {
            throw new JMLNoSuchElementException();
        }
    }

    /** Return the next pair in this, if there is one.
     *  @exception JMLNoSuchElementException when this is empty.
     *  @see #hasMoreElements
     *  @see #nextElement
     */
    /*@ public normal_behavior
      @    requires !uniteratedPairs.isEmpty();
      @    assignable uniteratedPairs, moreElements;
      @    ensures \old(uniteratedPairs).has(\result)
      @         && uniteratedPairs
      @            .equals(\old(uniteratedPairs).remove(\result));
      @ also
      @  public exceptional_behavior
      @    requires uniteratedPairs.isEmpty();
      @    assignable \nothing;
      @    signals_only JMLNoSuchElementException;
      @
      @ also
      @   modifies uniteratedPairs;
      @   modifies moreElements;
      @   ensures \old(moreElements);
      @   signals_only JMLNoSuchElementException;
      @   signals (JMLNoSuchElementException) \old(!moreElements);
      @*/
    public /*@ non_null @*/ JMLObjectEqualsPair<K, V> nextPair()
        throws JMLNoSuchElementException {
        JMLObjectEqualsPair<K, V> aPair = nextElement();  //@ nowarn Pre;
        //@ assume aPair != null;
        return aPair;
    } //@ nowarn NonNullResult, Post;

    /** Return a clone of this enumerator.
     */
    public /*@ non_null @*/ Object clone() {
        return new JMLObjectToEqualsRelationEnumerator<K, V>(imagePairEnum,
                                                          imageEnum,
                                                          key);
    } //@ nowarn Post;

    /** Return true just when this enumerator has the same state as
     *  the given argument.
     */
    public /*@ pure @*/ boolean equals(/*@ nullable @*/ Object oth) {
        if  (oth == null
             || !(oth instanceof JMLObjectToEqualsRelationEnumerator)) {
            return false;
        } else {
            JMLObjectToEqualsRelationEnumerator<K, V> js
                = (JMLObjectToEqualsRelationEnumerator<K, V>) oth;
            return abstractValue().equals(js.abstractValue());
        }
    }

    /** Return a hash code for this enumerator.
     */
    public /*@ pure @*/ int hashCode() {
        return abstractValue().hashCode();
    }

    /** Return the set of uniterated pairs from this enumerator.
     */
    protected /*@ spec_public pure @*/ /*@ non_null @*/
        JMLValueSet<JMLObjectEqualsPair<K, V>> abstractValue()
    {
        JMLValueSet<JMLObjectEqualsPair<K, V>> ret = new JMLValueSet<JMLObjectEqualsPair<K, V>>();
        JMLObjectToEqualsRelationEnumerator<K, V> enum2
            = (JMLObjectToEqualsRelationEnumerator<K, V>) clone();
        while (enum2.hasMoreElements()) {
            //@ assume enum2.moreElements;
            JMLObjectEqualsPair<K, V> aPair = enum2.nextPair();
            ret = ret.insert(aPair);
        }
        return ret;
    } //@ nowarn Exception;
}
