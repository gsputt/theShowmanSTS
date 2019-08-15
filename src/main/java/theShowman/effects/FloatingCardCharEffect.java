package theShowman.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class FloatingCardCharEffect {


    private TextureRegion card;
    private float startingX;
    private float startingY;
    public float currentX;
    public float currentY;
    private float bobX;
    private float bobY;
    private float speed;
    public float destinationX;
    public float destinationY;
    private float tempX;
    private float tempY;
    public float rotation;
    private float tempRotation;
    private float destinationRotation;
    private float sourceRotation;

    private float timer;
    private boolean beingSentToLocation;
    private boolean triggerOnce;
    private boolean returning;

    public FloatingCardCharEffect(float startingX, float startingY, float bobX, float bobY, float speed, float rotation)
    {
        this.currentX = this.destinationX = this.startingX = startingX;
        this.currentY = this.destinationY = this.startingY = startingY;
        this.bobX = bobX;
        this.bobY = bobY;
        this.timer = 0.0F;
        this.speed = speed;
        this.beingSentToLocation = false;
        triggerOnce = false;
        this.sourceRotation = this.rotation = rotation;
    }

    public void sendToLocation(float destinationX, float destinationY)
    {
        beingSentToLocation = true;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.destinationRotation = MathUtils.atan2(destinationX - currentX, destinationY - currentY);
        this.triggerOnce = true;
    }

    public void update()
    {
        if(!beingSentToLocation && !returning) {
            Vector2 vector = new Vector2(MathUtils.sin(this.timer) * bobX, MathUtils.sin(this.timer) * bobY);
            vector.rotate(this.rotation);
            this.currentX = this.startingX + vector.x;
            this.currentY = this.startingY + vector.y;
        }
        else if(beingSentToLocation)
        {
            if(triggerOnce)
            {
                triggerOnce = false;
                this.timer = 0.0F;
                this.tempX = this.currentX;
                this.tempY = this.currentY;
                this.tempRotation = this.rotation;
            }
            if(this.timer > 0.25F)
            {
                beingSentToLocation = false;
                returning = true;
                this.timer = 0.0F;
                this.tempX = this.currentX;
                this.tempY = this.currentY;
                this.tempRotation = this.rotation;
            }
            else {
                this.currentX = Interpolation.linear.apply(this.tempX, this.destinationX, this.timer / 0.25F);
                this.currentY = Interpolation.linear.apply(this.tempY, this.destinationY, this.timer / 0.25F);
                this.rotation = Interpolation.pow4Out.apply(this.tempRotation, this.destinationRotation, this.timer / 0.25F);
            }
        }
        else if(returning)
        {

            if(this.timer > 0.25F)
            {
                this.returning = false;
                this.timer = 0.0F;
            }
            else
            {
                this.currentX = Interpolation.linear.apply(this.tempX, this.startingX, this.timer / 0.25F);
                this.currentY = Interpolation.linear.apply(this.tempY, this.startingY, this.timer / 0.25F);
                this.rotation = Interpolation.pow4In.apply(this.tempRotation, this.sourceRotation, this.timer / 0.25F);
            }
        }
        this.timer += Gdx.graphics.getDeltaTime() * speed;
    }

}
