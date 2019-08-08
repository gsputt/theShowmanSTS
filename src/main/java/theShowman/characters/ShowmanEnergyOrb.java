package theShowman.characters;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;


public class ShowmanEnergyOrb extends CustomEnergyOrb {

    private static final float ORB_IMG_SCALE;
    private static final String rabbitTextureString = "theShowmanResources/images/char/showmanCharacter/orb/rabbit.png";
    private static final Texture rabbitTexture = new Texture(rabbitTextureString);

    private static final String[] orbTextures = {
            "theShowmanResources/images/char/showmanCharacter/orb/layer1.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer2.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer3.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer4.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer5.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer6.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer1d.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer2d.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer3d.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer4d.png",
            "theShowmanResources/images/char/showmanCharacter/orb/layer5d.png"};
    private static final String orbVFXPath = "theShowmanResources/images/char/showmanCharacter/orb/vfx.png";

    private boolean renderRabbit = false;
    private float YShift;

    public ShowmanEnergyOrb()
    {
        super(orbTextures, orbVFXPath, null);
        renderRabbit = false;
        YShift = 0.0F;
    }

    @Override
    public void updateOrb(int energyCount)
    {
        super.updateOrb(energyCount);
        if(this.angles[1] > 360F)
        {
            while(this.angles[1] > 360F)
            {
                this.angles[1] -= 360F;
            }
        }
        if(this.angles[1] > 45 && this.angles[1] < 225)
        {
            renderRabbit = true;
            if(this.angles[1] > 45 && this.angles[1] < 75)
            {
                YShift = Interpolation.linear.apply(0, 100F, (this.angles[1]-45) / 30);
            }
            else if(this.angles[1] > 195 && this.angles[1] < 225)
            {
                YShift = Interpolation.linear.apply(100F, 0, (this.angles[1]-195) / 30);
            }
        }
        else {
            renderRabbit = false;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        if(renderRabbit)
        {
            Vector2 vector = new Vector2(0, YShift);
            vector.rotate(this.angles[1] - 135);
            vector.scl(Settings.scale);

            sb.draw(rabbitTexture, current_x - 64F + vector.x, current_y - 64F + vector.y, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[1] - 135, 0, 0, 128, 128, false, false);
        }
        super.renderOrb(sb, enabled, current_x, current_y);
    }
    static {
        ORB_IMG_SCALE = 1.15F * Settings.scale;// 15
    }
}
