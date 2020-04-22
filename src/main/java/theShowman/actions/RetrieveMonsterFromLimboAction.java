package theShowman.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static theShowman.patches.ColumbifyTrackingFields.limboMonsterGroupSpireField.limboMonsterGroup;
import static theShowman.patches.ColumbifyTrackingFields.redirectTargetField.imInLimboField;

public class RetrieveMonsterFromLimboAction extends AbstractGameAction {
    private AbstractRoom room;
    private AbstractMonster monster;
    boolean toKill;

    public RetrieveMonsterFromLimboAction(AbstractRoom returnToHere, AbstractMonster freeMee, boolean murder)
    {
        room = returnToHere;
        monster = freeMee;
        toKill = murder;
    }

    public RetrieveMonsterFromLimboAction(AbstractRoom returnToHere, AbstractMonster freeMee)
    {
        this(returnToHere, freeMee, false);
    }

    public void update()
    {
        this.isDone = true;
        MonsterGroup limbo = limboMonsterGroup.get(room);
        if(limbo.monsters.size() > 0 && limbo.monsters.contains(monster))
        {
            room.monsters.add(monster);
            limbo.monsters.remove(monster);
            imInLimboField.set(monster, false);
            Color transparent = Color.WHITE.cpy();
            transparent.a = 0.0F;
            monster.tint.color = transparent.cpy();
            monster.tint.changeColor(Color.WHITE.cpy(), 3.0F);
            if(toKill)
            {
                monster.currentHealth = 0;
                monster.healthBarUpdatedEvent();
                monster.damage(new DamageInfo((AbstractCreature)null, 0, DamageInfo.DamageType.HP_LOSS));
            }

        }
    }

}
