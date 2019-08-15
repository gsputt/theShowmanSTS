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
    private static final String hatBackString = "theShowmanResources/images/char/showmanCharacter/orb/orb_hat_back.png";
    private static final Texture hatBackTexture = new Texture(hatBackString);

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
        if(this.angles[1] > 90 && this.angles[1] < 270)
        {
            renderRabbit = true;
            if(this.angles[1] > 90 && this.angles[1] < 120)
            {
                YShift = Interpolation.linear.apply(0, 85F, (this.angles[1]-90) / 30);
            }
            else if(this.angles[1] > 240 && this.angles[1] < 270)
            {
                YShift = Interpolation.linear.apply(85F, 0, (this.angles[1]-240) / 30);
            }
        }
        else {
            renderRabbit = false;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        sb.draw(hatBackTexture, current_x - 64F, current_y - 64F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[1], 0, 0, 128, 128, false, false);

        if(renderRabbit)
        {
            Vector2 vector = new Vector2(0, YShift);
            vector.rotate(this.angles[1] - 180);
            vector.scl(Settings.scale);

            sb.draw(rabbitTexture, current_x - 64F + vector.x, current_y - 64F + vector.y, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angles[1] - 180, 0, 0, 128, 128, false, false);
        }
        super.renderOrb(sb, enabled, current_x, current_y);
    }
    static {
        ORB_IMG_SCALE = 1.15F * Settings.scale;// 15
    }
}
