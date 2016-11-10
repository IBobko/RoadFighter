package uma.roadfighter.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class RoadFighterGLView extends GLSurfaceView {
	RoadFighterGLRenderer renderer;
	
	RoadFighterGLView(Context context) {
		super(context);
		
		// Renderer
		renderer = new RoadFighterGLRenderer(context);
		setRenderer(renderer);
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent e ) {
		float x = e.getX();
		float y = e.getY();
		
		switch( e.getAction() ) {
		case MotionEvent.ACTION_DOWN:
			if( ( y > 3 * getHeight() / 4 ) && ( x < 1 * getWidth() / 4 ) )
				renderer.event = 'l';
			if( ( y > 3 * getHeight() / 4 ) && ( x > 3 * getWidth() / 4 ) )
				renderer.event = 'r';
			else if( y < getHeight() / 2 )
				renderer.event = 'n';

            System.out.println("Height " + getHeight() + ";width=" + getWidth());
			System.out.println("DOWN " + "x=" + x + ";y=" + y);
            System.out.println("Event: " + renderer.event);

			break;
		case MotionEvent.ACTION_UP:
			if( ( y > 3 * getHeight() / 4 ) && ( x < 2 * getWidth() / 4 ) && ( x > 1 * getWidth() / 4 ) )
				renderer.event = 'a';
			else if( ( y > 3 * getHeight() / 4 ) && ( x > 2 * getWidth() / 4 ) && ( x < 3 * getWidth() / 4 ) )
				renderer.event = 'h';
			else
				renderer.event = 'c';

            System.out.println("UP " + "x=" + x + ";y=" + y);
            System.out.println("Event: " + renderer.event);
			break;
		}
		
		return true;
	}
}
