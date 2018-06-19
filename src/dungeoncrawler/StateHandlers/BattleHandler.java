/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.StateHandlers;

import dungeoncrawler.Abilities.Animations.DeathAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.MonsterGroups.Dragons;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;

/**
 *
 * @author Nick
 */
public class BattleHandler {

    public void CalcPercHealth() {
        double HP = DungeonCrawler.War.HP + DungeonCrawler.Ftr.HP + DungeonCrawler.Blm.HP;
        double MaxHP = DungeonCrawler.War.MaxHP + DungeonCrawler.Ftr.MaxHP + DungeonCrawler.Blm.MaxHP;
        PercentHealth = HP / MaxHP;
    }
    public static int numberOfTurns;
    public static double PercentHealth;
    public static MonsterGroup Enemies;
    public static LinkedList<String> BattleText = new LinkedList<>();
    public static int TextSlider = 0;
    public static int HeroLevel = 3;
    public static int MonsterLevel = 2;
    public boolean HerosTurn = false;
    public boolean BattleOver = false;
    public static Image target = ImageHandler.Target;
    public BattleHandlerDraw BattleDrawer = new BattleHandlerDraw();

    public enum BattleState {

        Ability, battleText, Hero, Monster
    };

    public static class Cursor {

        int selectAbility = 1;
        int selectMonster = 0;
        int selectHero = 1;
        int selectHeroAsTarget = 1;
        public BattleState Cursorstate = BattleState.Ability;
    }
    public static Cursor cursor = new Cursor();

    public void InitalizeHero(Hero hero) {
        hero.turnDone = false;
        hero.HP = hero.MaxHP;
        hero.isAlive = true;
        hero.Defending = false;
        hero.SetAbilityUsable();
        hero.InitializeAnimations();
    }

    public void InitalizeBattle(MonsterGroup encounter) {
        numberOfTurns = 0;
        DungeonCrawler.GameState = DungeonCrawler.State.BATTLE;
        BattleOver = false;

        Enemies = encounter;
        Enemies.LevelUp(MonsterLevel);
        whichMonstersTurn = 0;

        BattleText.clear();
        TextSlider = 0;
        addBattleText("You Encounter " + Enemies.name + "!");

        cursor.selectMonster = 0;
        cursor.selectAbility = 1;
        cursor.Cursorstate = BattleState.Ability;

        DungeonCrawler.Blm.Mana = 5;
        DungeonCrawler.Blm.imbued = false;
        DungeonCrawler.Blm.Absorb = false;

        DungeonCrawler.Ftr.Rage = 3;
        DungeonCrawler.Ftr.isCharged = false;
        DungeonCrawler.Ftr.ChargeMonster = 0;

        DungeonCrawler.War.useMagicSetAbility = false;
        DungeonCrawler.War.TauntHealCD = 0;
        DungeonCrawler.War.SlashBashCD = 0;

        InitalizeHero(DungeonCrawler.Blm);
        InitalizeHero(DungeonCrawler.War);
        InitalizeHero(DungeonCrawler.Ftr);
        HerosTurn = false;

        SetHeroBattleLocation(DungeonCrawler.Ftr, 150);
        SetHeroBattleLocation(DungeonCrawler.War, 250);
        SetHeroBattleLocation(DungeonCrawler.Blm, 350);
    }

    public static void addBattleText(String text) {
        if (BattleText.size() > 9) {
            TextSlider++;
        }
        BattleText.push(text);

    }

    public void SetHeroBattleLocation(Hero hero, int y) {
        hero.X = 100;
        hero.Y = y;
    }

    public void TurnOnHeroTurn(Hero hero) {
        if (hero.isAlive) {
            hero.turnDone = false;
        }
    }
    int whichMonstersTurn = 0;

    public void BattleLoop() {

        DungeonCrawler.Ftr.AnimateAttacks();
        DungeonCrawler.War.AnimateAttacks();
        DungeonCrawler.Blm.AnimateAttacks();

        for (int i = 0; i < Enemies.monsterList.size(); i++) {
            Enemies.monsterList.get(i).AnimateAttacks();
        }

        if (!BattleOver) {
            if (HerosTurn) {

                if (DungeonCrawler.Ftr.UseChargeAttack()) {
                    BattleState CursorSave = cursor.Cursorstate;
                    HeroUsedAbility(DungeonCrawler.Ftr);
                    cursor.Cursorstate = CursorSave;
                }

                if (AreHerosDone()) {
                    if (!AreHerosAnimating()) {
                        HerosTurn = false;
                        numberOfTurns++;
                        TurnOnHeroTurn(DungeonCrawler.Ftr);
                        TurnOnHeroTurn(DungeonCrawler.War);
                        TurnOnHeroTurn(DungeonCrawler.Blm);
                    }
                }

            }
            if (!HerosTurn) {
                if (!AreMonstersAnimating()) {
                    if (whichMonstersTurn < Enemies.monsterList.size()) {
                        MonsterAttacks(whichMonstersTurn);
                        whichMonstersTurn++;

                        CalcPercHealth();
                    }
                }

                if (whichMonstersTurn >= Enemies.monsterList.size()) {
                    if (!AreMonstersAnimating()) {
                        whichMonstersTurn = 0;
                        HerosTurn = true;
                        DungeonCrawler.Blm.Absorb = false;
                    }
                }

            }
        }

        if (BattleOver) {
            if (!AreMonstersAnimating()) {
                if (DungeonCrawler.AnimationList.isEmpty()) {
                    ResetHerosAfterBattle();
                    if (DidHerosWin()) {
                        if (Enemies.getClass() == Dragons.class) {
                            DungeonCrawler.GameState = DungeonCrawler.State.LVLUP;
                            RewardHandler.levelUpHeros();
                        } else {
                            DungeonCrawler.GameState = DungeonCrawler.State.STATS;
                            RewardHandler.CreateNewItem();
                        }
                    } else {
                        if (MonsterLevel == HeroLevel) {
                            WorldHandler.isDragonHere = true;
                        }
                        DungeonCrawler.GameState = DungeonCrawler.State.WORLD;
                    }
                }
            }
        }
    }

    public boolean AreHerosDone() {
        if (DungeonCrawler.Ftr.turnDone) {
            if (DungeonCrawler.War.turnDone) {
                if (DungeonCrawler.Blm.turnDone) {
                    return true;
                }
            }
        }

        return false;
    }

    public void checkDeadMonsters() {
        for (int mons = 0; mons < Enemies.monsterList.size(); mons++) {
            if (Enemies.monsterList.get(mons).HP < 0) {
                addBattleText(Enemies.monsterList.get(mons).name + " dies");
                Enemies.monsterList.remove(mons);
                if (cursor.selectMonster >= Enemies.monsterList.size()) {
                    cursor.selectMonster = Enemies.monsterList.size() - 1;
                }
                mons--;
            }
        }
    }

    public void killHero(Hero hero) {
        if (hero.isAlive) {
            hero.isAlive = false;
            hero.HP = 0;
            hero.turnDone = true;
            addBattleText(hero.name + " dies");
        }
    }

    public void checkDeadHeros() {
        if (DungeonCrawler.War.HP <= 0) {
            killHero(DungeonCrawler.War);
        }
        if (DungeonCrawler.Ftr.HP <= 0) {
            killHero(DungeonCrawler.Ftr);
        }
        if (DungeonCrawler.Blm.HP <= 0) {
            killHero(DungeonCrawler.Blm);
        }

    }

    public boolean DidHerosWin() {
        if (Enemies.monsterList.size() == 0) {
            return true;
        }

        return false;
    }

    public boolean DidHerosLose() {
        if (!DungeonCrawler.War.isAlive) {
            if (!DungeonCrawler.Ftr.isAlive) {
                if (!DungeonCrawler.Blm.isAlive) {
                    return true;
                }
            }
        }


        return false;
    }

    public void EndHeroBattle(Hero hero) {
        hero.isAlive = true;
        hero.Defending = false;
    }

    public void ResetHerosAfterBattle() {
        DungeonCrawler.ResetHeroPosition(50, 200);
        EndHeroBattle(DungeonCrawler.War);
        EndHeroBattle(DungeonCrawler.Ftr);
        EndHeroBattle(DungeonCrawler.Blm);
    }

    public void EndBattle() {
        WorldHandler.ResetQueueFromBattle = true;
        DungeonCrawler.Blm.imbued = false;
        DungeonCrawler.Blm.Absorb = false;
        DungeonCrawler.Ftr.isCharged = false;

        BattleOver = true;

    }

    public void MonsterAttacks(int i) {

        Enemies.monsterList.get(i).UseAbility(Enemies);
        Enemies.monsterList.get(i).AttackAnimate = true;
        checkDeadHeros();
        if (DidHerosLose()) {
            DungeonCrawler.AnimationList.push(new DeathAnimation(Enemies));
            EndBattle();
        }

    }

    public boolean AreMonstersAnimating() {
        for (int i = 0; i < Enemies.monsterList.size(); i++) {
            if (Enemies.monsterList.get(i).AttackAnimate) {
                return true;
            }
        }
        return false;
    }

    public boolean AreHerosAnimating() {
        if (DungeonCrawler.War.AttackAnimate) {
            return true;
        }
        if (DungeonCrawler.Ftr.AttackAnimate) {
            return true;
        }
        if (DungeonCrawler.Blm.AttackAnimate) {
            return true;
        }
        return false;
    }

    public void drawBattleScene(Graphics2D g2d) {
        BattleDrawer.drawBattleSceneFromDrawer(g2d);
    }

    public Hero getHeroFromCursor(int heroCursor) {
        if (heroCursor == 1) {
            return DungeonCrawler.Ftr;
        }
        if (heroCursor == 2) {
            return DungeonCrawler.War;
        }
        if (heroCursor == 3) {
            return DungeonCrawler.Blm;
        }
        return null;
    }

    public void lefttHero() {
        do {
            cursor.selectHero--;
            if (cursor.selectHero < 1) {
                cursor.selectHero = 3;
            }
        } while (getHeroFromCursor(cursor.selectHero).turnDone == true);
    }

    public void rightHero() {
        do {
            cursor.selectHero++;
            if (cursor.selectHero > 3) {
                cursor.selectHero = 1;
            }
        } while (getHeroFromCursor(cursor.selectHero).turnDone == true);
    }

    public void handleLeft() {
        if (cursor.Cursorstate == BattleState.Ability) {
            if (!AreHerosDone()) {
                lefttHero();
            }
            upAbility();
            downAbility();
        }
    }

    public void handleRight() {
        if (cursor.Cursorstate == BattleState.Ability) {
            if (!AreHerosDone()) {
                rightHero();
            }
            upAbility();
            downAbility();
        }
    }

    public void upAbility() {
        do {
            cursor.selectAbility--;
            if (cursor.selectAbility < 1) {
                cursor.selectAbility = 6;
            }
        } while (getHeroFromCursor(cursor.selectHero).abilityList.get(cursor.selectAbility - 1).Usable == false);
    }

    public void downAbility() {
        do {
            cursor.selectAbility++;
            if (cursor.selectAbility > 6) {
                cursor.selectAbility = 1;
            }
        } while (getHeroFromCursor(cursor.selectHero).abilityList.get(cursor.selectAbility - 1).Usable == false);
    }

    public void handleUp() {
        if (cursor.Cursorstate == BattleState.Ability) {
            upAbility();
        }

        if (cursor.Cursorstate == BattleState.battleText) {
            TextSlider--;
            if (TextSlider < 0) {
                TextSlider = 0;
            }
        }

        if (cursor.Cursorstate == BattleState.Hero) {
            cursor.selectHeroAsTarget--;
            if (cursor.selectHeroAsTarget < 1) {
                cursor.selectHeroAsTarget = 3;
            }
        }

        if (cursor.Cursorstate == BattleState.Monster) {
            cursor.selectMonster--;
            if (cursor.selectMonster < 0) {
                cursor.selectMonster = Enemies.monsterList.size() - 1;
            }
        }
    }

    public void handleDown() {
        if (cursor.Cursorstate == BattleState.Ability) {
            downAbility();
        }

        if (cursor.Cursorstate == BattleState.battleText) {
            if (BattleText.size() - TextSlider > 9) {
                TextSlider++;
                if (TextSlider >= BattleText.size()) {
                    TextSlider = BattleText.size() - 1;
                }
            }
        }

        if (cursor.Cursorstate == BattleState.Hero) {
            cursor.selectHeroAsTarget++;
            if (cursor.selectHeroAsTarget > 3) {
                cursor.selectHeroAsTarget = 1;
            }
        }

        if (cursor.Cursorstate == BattleState.Monster) {
            cursor.selectMonster++;
            if (cursor.selectMonster >= Enemies.monsterList.size()) {
                cursor.selectMonster = 0;
            }
        }
    }

    public void HeroUsedAbility(Hero hero) {
        hero.turnDone = true;
        hero.AttackAnimate = true;
        cursor.Cursorstate = BattleState.Ability;
        hero.SetAbilityUsable();
        checkDeadMonsters();
        if (DidHerosWin()) {
            EndBattle();
        }
    }

    public void UseHeroAbility(Hero hero) {
        if (!hero.turnDone) {
            saveFighterChargeTarget();
            hero.abilityList.get(cursor.selectAbility - 1).ActivateAbility(hero, Enemies.monsterList.get(cursor.selectMonster), getHeroFromCursor(cursor.selectHeroAsTarget));
            HeroUsedAbility(hero);

        }
    }

    public void saveFighterChargeTarget() {
        if (cursor.selectAbility == 2) {
            if (cursor.selectHero == 1) {
                DungeonCrawler.Ftr.ChargeMonster = cursor.selectMonster;
            }
        }
    }

    public void handleBack() {
        if (cursor.Cursorstate == BattleState.battleText) {
            cursor.Cursorstate = BattleState.Ability;
            return;
        }

        if (cursor.Cursorstate == BattleState.Ability) {
            cursor.Cursorstate = BattleState.battleText;
        }

        if (cursor.Cursorstate == BattleState.Monster || cursor.Cursorstate == BattleState.Hero) {
            cursor.Cursorstate = BattleState.Ability;
        }
    }

    public void heroTurnUseAbility() {
        if (HerosTurn) {
            if (!BattleOver) {
                UseHeroAbility(getHeroFromCursor(cursor.selectHero));
            }
        }
    }

    public void handleConfirm() {

        if (cursor.Cursorstate == BattleState.Ability) {
            if (!getHeroFromCursor(cursor.selectHero).turnDone) {
                if (getHeroFromCursor(cursor.selectHero).abilityList.get(cursor.selectAbility - 1).Usable) {

                    if (getHeroFromCursor(cursor.selectHero).abilityList.get(cursor.selectAbility - 1).noTarget) {
                        heroTurnUseAbility();
                    } else if (getHeroFromCursor(cursor.selectHero).abilityList.get(cursor.selectAbility - 1).targetHero) {
                        cursor.Cursorstate = BattleState.Hero;
                    } else {
                        cursor.Cursorstate = BattleState.Monster;
                    }

                    return;
                }
            }
        }

        if (cursor.Cursorstate == BattleState.Monster) {
            heroTurnUseAbility();
        }

        if (cursor.Cursorstate == BattleState.Hero) {
            if (getHeroFromCursor(cursor.selectHeroAsTarget).isAlive) {
                heroTurnUseAbility();
            }
        }


    }
}
