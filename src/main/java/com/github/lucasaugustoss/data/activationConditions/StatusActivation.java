package com.github.lucasaugustoss.data.activationConditions;

public enum StatusActivation {
/* MOMENTO */

    AfterCountDown,
    BeforeHit,
    CauserLeaveField,
    ChangeMove,
    DeductHP,
    EndOfTurn,
    Faint,
    Start,
    UseMove,


/* CÁLCULOS */

    CritRatioCalc,
    DamageCalc,
    OpponentDamageCalc,


/* AÇÕES */

    Hit,
    OpponentHitGuarantee,
    OpponentMove,
    OpponentTryAct,
    OpponentTrySelectMove,
    OpponentTryUseMoveAny,
    OpponentTryUseMoveTargeted,
    TryAct,
    TryForceSwitch,
    TryMove,
    TrySelectMove,
    TrySwitch,


/* EFEITOS */

    Invulnerability,
    PostHitMessage,
    PrimaryEffectActivation,
    SecondaryEffectActivation,
}