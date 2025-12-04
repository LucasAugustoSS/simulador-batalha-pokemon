package data.activationConditions;

public enum StatusActivation {
/* MOMENTO */

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