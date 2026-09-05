package com.github.lucasaugustoss.data.activationConditions;

public enum ItemActivation {
/* MOMENTO */

    AfterMove,
    Consumed,
    DeductHP,
    Eat,
    EndOfTurn,
    Entry,
    ForceUse,
    Given,
    Pinch,


/* CÁLCULOS */

    AttackCalc,
    DefenseCalc,
    SpecialAttackCalc,
    SpecialDefenseCalc,
    SpeedCalc,

    DamageCalc,
    PowerCalc,


/* AÇÕES */

    HitUser,
    TrySelectMove,
    TryUseMove,
    UseMove
}
