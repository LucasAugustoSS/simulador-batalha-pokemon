package data.activationConditions;

public enum MoveEffectActivation {
/* MOMENTO */

    AfterMove,
    AfterMoveMultiHit,
    BeforeMove,
    DelayedSwitch,
    DelayedTurnEnd,
    Miss,
    OpponentMove,
    OpponentSwitch,
    TryUse,
    TurnStart,


/* CÁLCULOS */

    AccuracyCalc,
    DamageCalc,
    EffectivenessCalc,


/* CHAMADAS */

    CallAttackingStat,
    CallCategory,
    CallDefendingStat,
    CallEffectTarget,
    CallHits,
    CallIneffective,
    CallMoveData,
    CallMoveTarget,
    CallNotVeryEffective,
    CallPower,
    CallSuperEffective,
    CallType,


/* PROPRIEDADES */

    ChangeTarget,
    FinalDamage,
    FixedDamage,
    HitGuarantee,
    OneHitKOAccuracy,
    TestImmunities,


/* Z-MOVES */

    ZNormal,
    ZPrimarySuccess,
    ZDelayedSwitch,
    ZCallType,
}