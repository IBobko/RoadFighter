package RoadFighter_sequential;

import eventb_prelude.*;
import Util.*;
//@ model import org.jmlspecs.models.JMLObjectSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ref0_circuit{
	private static final Integer max_integer = Utilities.max_integer;
	private static final Integer min_integer = Utilities.min_integer;

	UPDATE_POS evt_UPDATE_POS = new UPDATE_POS(this);
	DELETE_OBSTACLE evt_DELETE_OBSTACLE = new DELETE_OBSTACLE(this);
	SET_LEFT_BORDER evt_SET_LEFT_BORDER = new SET_LEFT_BORDER(this);
	DELETE_CAR evt_DELETE_CAR = new DELETE_CAR(this);
	SET_RIGHT_BORDER evt_SET_RIGHT_BORDER = new SET_RIGHT_BORDER(this);
	CAR_RESET evt_CAR_RESET = new CAR_RESET(this);
	ADD_LANE evt_ADD_LANE = new ADD_LANE(this);
	DELETE_LANE evt_DELETE_LANE = new DELETE_LANE(this);
	ADD_OBSTACLE evt_ADD_OBSTACLE = new ADD_OBSTACLE(this);
	SET_POS evt_SET_POS = new SET_POS(this);
	ADD_OBJECT evt_ADD_OBJECT = new ADD_OBJECT(this);
	SET_FINISH_LINE evt_SET_FINISH_LINE = new SET_FINISH_LINE(this);
	ADD_CAR evt_ADD_CAR = new ADD_CAR(this);


	/******Set definitions******/
	//@ public static constraint LANES.equals(\old(LANES)); 
	public static final BSet<Integer> LANES = new Enumerated(min_integer,max_integer);

	//@ public static constraint OBJECTS.equals(\old(OBJECTS)); 
	public static final BSet<Integer> OBJECTS = new Enumerated(min_integer,max_integer);


	/******Constant definitions******/
	//@ public static constraint USER_CAR.equals(\old(USER_CAR)); 
	public static final Integer USER_CAR = Test_ref0_circuit.random_USER_CAR;

	//@ public static constraint USER_LANE.equals(\old(USER_LANE)); 
	public static final Integer USER_LANE = Test_ref0_circuit.random_USER_LANE;



	/******Axiom definitions******/
	/*@ public static invariant INT.instance.has(USER_LANE); */
	/*@ public static invariant INT.instance.has(USER_CAR); */


	/******Variable definitions******/
	/*@ spec_public */ private BSet<Integer> cars;

	/*@ spec_public */ private BRelation<Integer,Integer> finish_line;

	/*@ spec_public */ private BRelation<Integer,Integer> height;

	/*@ spec_public */ private BSet<Integer> lanes;

	/*@ spec_public */ private BRelation<Integer,Integer> left_border;

	/*@ spec_public */ private BRelation<Integer,Integer> obj_desc;

	/*@ spec_public */ private BSet<Integer> objects;

	/*@ spec_public */ private BSet<Integer> obstacles;

	/*@ spec_public */ private BRelation<Integer,Integer> posX;

	/*@ spec_public */ private BRelation<Integer,Integer> posY;

	/*@ spec_public */ private BRelation<Integer,Integer> right_border;

	/*@ spec_public */ private BRelation<Integer,Integer> width;




	/******Invariant definition******/
	/*@ public invariant
		objects.isSubset(OBJECTS) &&
		obstacles.isSubset(objects) &&
		cars.isSubset(objects) &&
		(cars.intersection(obstacles)).equals(BSet.EMPTY) &&
		lanes.isSubset(LANES) &&
		 finish_line.domain().equals(lanes) && finish_line.range().isSubset(INT.instance) && finish_line.isaFunction() && BRelation.cross(lanes,INT.instance).has(finish_line) &&
		 left_border.domain().equals(lanes) && left_border.range().isSubset(INT.instance) && left_border.isaFunction() && BRelation.cross(lanes,INT.instance).has(left_border) &&
		 right_border.domain().equals(lanes) && right_border.range().isSubset(INT.instance) && right_border.isaFunction() && BRelation.cross(lanes,INT.instance).has(right_border) &&
		 posX.domain().equals(objects) && posX.range().isSubset(INT.instance) && posX.isaFunction() && BRelation.cross(objects,INT.instance).has(posX) &&
		 posY.domain().equals(objects) && posY.range().isSubset(INT.instance) && posY.isaFunction() && BRelation.cross(objects,INT.instance).has(posY) &&
		 width.domain().equals(objects) && width.range().isSubset(NAT.instance) && width.isaFunction() && BRelation.cross(objects,NAT.instance).has(width) &&
		 height.domain().equals(objects) && height.range().isSubset(NAT.instance) && height.isaFunction() && BRelation.cross(objects,NAT.instance).has(height) &&
		 obj_desc.domain().equals(objects) && obj_desc.range().isSubset(NAT.instance) && obj_desc.isaFunction() && BRelation.cross(objects,NAT.instance).has(obj_desc) &&
		 (\forall Integer obj1;  (\forall Integer obj2;objects.has(obj1) && objects.has(obj2) && !obj1.equals(obj2) && (new Integer(posX.apply(obj1) - new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) - new Integer(width.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posX.apply(obj1) - new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) + new Integer(width.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posY.apply(obj1) - new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) - new Integer(height.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posY.apply(obj1) - new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) + new Integer(height.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posX.apply(obj1) - new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) - new Integer(width.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posX.apply(obj1) - new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) + new Integer(width.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posY.apply(obj1) + new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) - new Integer(height.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posY.apply(obj1) + new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) + new Integer(height.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posX.apply(obj1) + new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) - new Integer(width.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posX.apply(obj1) + new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) + new Integer(width.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posY.apply(obj1) + new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) - new Integer(height.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posY.apply(obj1) + new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) + new Integer(height.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posX.apply(obj1) + new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) - new Integer(width.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posX.apply(obj1) + new Integer(width.apply(obj1) / new Integer(2)))).compareTo(new Integer(posX.apply(obj2) + new Integer(width.apply(obj2) / new Integer(2)))) < 0 && (new Integer(posY.apply(obj1) - new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) - new Integer(height.apply(obj2) / new Integer(2)))) > 0 && (new Integer(posY.apply(obj1) - new Integer(height.apply(obj1) / new Integer(2)))).compareTo(new Integer(posY.apply(obj2) + new Integer(height.apply(obj2) / new Integer(2)))) < 0)); */


	/******Getter and Mutator methods definition******/
	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.posX;*/
	public /*@ pure */ BRelation<Integer,Integer> get_posX(){
		return this.posX;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.posX;
	    ensures this.posX == posX;*/
	public void set_posX(BRelation<Integer,Integer> posX){
		this.posX = posX;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.cars;*/
	public /*@ pure */ BSet<Integer> get_cars(){
		return this.cars;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.cars;
	    ensures this.cars == cars;*/
	public void set_cars(BSet<Integer> cars){
		this.cars = cars;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.posY;*/
	public /*@ pure */ BRelation<Integer,Integer> get_posY(){
		return this.posY;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.posY;
	    ensures this.posY == posY;*/
	public void set_posY(BRelation<Integer,Integer> posY){
		this.posY = posY;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.finish_line;*/
	public /*@ pure */ BRelation<Integer,Integer> get_finish_line(){
		return this.finish_line;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.finish_line;
	    ensures this.finish_line == finish_line;*/
	public void set_finish_line(BRelation<Integer,Integer> finish_line){
		this.finish_line = finish_line;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.objects;*/
	public /*@ pure */ BSet<Integer> get_objects(){
		return this.objects;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.objects;
	    ensures this.objects == objects;*/
	public void set_objects(BSet<Integer> objects){
		this.objects = objects;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.obstacles;*/
	public /*@ pure */ BSet<Integer> get_obstacles(){
		return this.obstacles;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.obstacles;
	    ensures this.obstacles == obstacles;*/
	public void set_obstacles(BSet<Integer> obstacles){
		this.obstacles = obstacles;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.lanes;*/
	public /*@ pure */ BSet<Integer> get_lanes(){
		return this.lanes;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.lanes;
	    ensures this.lanes == lanes;*/
	public void set_lanes(BSet<Integer> lanes){
		this.lanes = lanes;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.right_border;*/
	public /*@ pure */ BRelation<Integer,Integer> get_right_border(){
		return this.right_border;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.right_border;
	    ensures this.right_border == right_border;*/
	public void set_right_border(BRelation<Integer,Integer> right_border){
		this.right_border = right_border;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.width;*/
	public /*@ pure */ BRelation<Integer,Integer> get_width(){
		return this.width;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.width;
	    ensures this.width == width;*/
	public void set_width(BRelation<Integer,Integer> width){
		this.width = width;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.obj_desc;*/
	public /*@ pure */ BRelation<Integer,Integer> get_obj_desc(){
		return this.obj_desc;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.obj_desc;
	    ensures this.obj_desc == obj_desc;*/
	public void set_obj_desc(BRelation<Integer,Integer> obj_desc){
		this.obj_desc = obj_desc;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.left_border;*/
	public /*@ pure */ BRelation<Integer,Integer> get_left_border(){
		return this.left_border;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.left_border;
	    ensures this.left_border == left_border;*/
	public void set_left_border(BRelation<Integer,Integer> left_border){
		this.left_border = left_border;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable \nothing;
	    ensures \result == this.height;*/
	public /*@ pure */ BRelation<Integer,Integer> get_height(){
		return this.height;
	}

	/*@ public normal_behavior
	    requires true;
	    assignable this.height;
	    ensures this.height == height;*/
	public void set_height(BRelation<Integer,Integer> height){
		this.height = height;
	}



	/*@ public normal_behavior
	    requires true;
	    assignable \everything;
	    ensures
		objects.isEmpty() &&
		obstacles.isEmpty() &&
		cars.isEmpty() &&
		lanes.isEmpty() &&
		finish_line.isEmpty() &&
		left_border.isEmpty() &&
		right_border.isEmpty() &&
		posX.isEmpty() &&
		posY.isEmpty() &&
		width.isEmpty() &&
		height.isEmpty() &&
		obj_desc.isEmpty();*/
	public ref0_circuit(){
		objects = new BSet<Integer>();
		obstacles = new BSet<Integer>();
		cars = new BSet<Integer>();
		lanes = new BSet<Integer>();
		finish_line = new BRelation<Integer,Integer>();
		left_border = new BRelation<Integer,Integer>();
		right_border = new BRelation<Integer,Integer>();
		posX = new BRelation<Integer,Integer>();
		posY = new BRelation<Integer,Integer>();
		width = new BRelation<Integer,Integer>();
		height = new BRelation<Integer,Integer>();
		obj_desc = new BRelation<Integer,Integer>();

	}
}