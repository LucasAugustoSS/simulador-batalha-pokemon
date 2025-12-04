package data.activationConditions;

public enum FieldActivation {
/* MOMENTO */

    BeforeEnd,
    EndOfTurn,
    EndOfTurnOnce,
    Entry,
    Repeat,
    Start,


/* CÁLCULOS */

    AccuracyCalc,
    DamageCalcAtk,
    DamageCalcDef,
    DefenseCalc,
    SpeedCalc,


/* AÇÕES */

    OpponentTryUseMove,
    TryAct,
    TrySelectMove,
    TryStatChange,
    TryStatus,
    TryUseItem,
    TryUseMove,


/* CHAMADAS */

    CallStatValue,
    CallSuperEffective,
    CallType,
}