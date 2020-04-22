package theShowman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static theShowman.patches.ColumbifyTrackingFields.limboMonsterGroupSpireField.limboMonsterGroup;
import static theShowman.patches.ColumbifyTrackingFields.redirectTargetField.imInLimboField;

public class SendMonsterToLimboAction extends AbstractGameAction {
    private AbstractMonster monster;
    private AbstractRoom room;

    public SendMonsterToLimboAction(AbstractRoom selectFromHere, AbstractMonster waitNoDontSendMeToLimbo)
    {
        room = selectFromHere;
        monster = waitNoDontSendMeToLimbo;
    }

    public void update()
    {
        this.isDone = true;
        if(room.monsters.monsters.contains(monster))
        {
            MonsterGroup limbo = limboMonsterGroup.get(room);
            imInLimboField.set(monster, true);
            limbo.add(monster);
            room.monsters.monsters.remove(monster);
        }
    }
}
