package com.github.lucasaugustoss.data.activationConditions;

public enum MoveEffectActivation {
/* MOMENTO */

    AfterCharge,
    AfterMove,
    AfterMoveCharged,
    AfterMoveMultiHit,
    BeforeMove,
    BeforeDamage,
    DelayedSwitch,
    DelayedTurnEnd,
    Miss,
    OpponentMove,
    OpponentSwitch,
    PrimarySuccess,
    TryActivate,
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