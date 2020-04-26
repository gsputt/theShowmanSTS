package theShowman.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import theShowman.effects.CurtainVFX;
import theShowman.effects.CurtainVFX2;

import java.util.ArrayList;

public class QueueSpeechEffect extends AbstractGameEffect {

    public float timeForBubble;
    public ArrayList<String> whatAmISaying;
    int sizeOfList;
    public String sayThis = null;
    int counter;
    SpeechBubble speech = null;
    boolean makeCurtainFast;
    boolean curtainDone;
    AbstractGameEffect curtain;

    public QueueSpeechEffect(ArrayList<String> whatToSay, float timeToDisplayBubble, boolean fastCurtain)
    {
        whatAmISaying = whatToSay;
        sizeOfList = whatAmISaying.size();
        counter = 0;
        timeForBubble = timeToDisplayBubble;
        makeCurtainFast = fastCurtain;
        curtainDone = false;
        curtain = null;
    }

    public void update()
    {
        if(counter < sizeOfList) {
            if(!curtainDone)
            {
                if(curtain == null) {
                    if (makeCurtainFast) {
                        curtain = new CurtainVFX2();
                    } else {
                        curtain = new CurtainVFX();
                    }
                    AbstractDungeon.effectsQueue.add(curtain);
                }
                else
                {
                    if(curtain.isDone)
                    {
                        curtainDone = true;
                    }
                }
            }
            else if (sayThis == null || speech == null) {
                sayThis = whatAmISaying.get(counter);
                speech = new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, timeForBubble, sayThis, true);
                AbstractDungeon.effectsQueue.add(0, speech);
            } else {
                if (speech.isDone) {
                    counter++;
                    sayThis = null;
                    speech = null;
                }
            }
        }
        else
        {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {

    }

    public void dispose()
    {

    }
}
