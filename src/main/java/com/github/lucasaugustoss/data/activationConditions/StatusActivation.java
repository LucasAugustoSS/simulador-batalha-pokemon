package com.github.lucasaugustoss.data.activationConditions;

public enum StatusActivation {
/* MOMENTO */

    AfterCountDown,
    BeforeHit,
    CauserLeaveField,
    ChangeMove,
    DeductHP,
    End,
    EndOfTurn,
    FailMove,
    Faint,
    PPChange,
    Start,
    UseMove,


/* CÁLCULOS */

    CritRatioCalc,
    DamageCalc,
    OpponentDamageCalc,
    OpponentAccuracyCalc,


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