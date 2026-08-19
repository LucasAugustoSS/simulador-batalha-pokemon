package com.github.lucasaugustoss.data.activationConditions;

public enum AbilityActivation {
/* CÁLCULOS */

    // stats

    AttackCalc,
    DefenseCalc,
    SpecialAttackCalc,
    SpecialDefenseCalc,
    SpeedCalc,
    AccuracyCalc,

    OpponentAttackCalc,
    OpponentSpecialAttackCalc,
    OpponentAccuracyCalc,

    // dano

    UserDamageCalc,
    UserPowerCalc,

    OpponentDamageCalc,
    OpponentPowerCalc,

    AnyPowerCalc,
    AnyStatCalc,

    // outros

    CritRatioCalc,
    EffectChanceCalc,
    PriorityCalc,
    STABCalc,
    WeightCalc,


/* CHAMADAS */

    // dados de Pokémon

    CallUserData,

    CallUserSuperEffective,
    CallUserNotVeryEffective,
    CallUserIneffective,

    CallOpponentStatStages,

    CallOpponentSuperEffective,
    CallOpponentNotVeryEffective,
    CallOpponentIneffective,

    ChangeOpponentSuperEffective,
    ChangeOpponentNotVeryEffective,
    ChangeOpponentIneffective,

    // dados de movimentos

    CallMove,
    CallContact,
    CallMoveType,
    CallHits,

    // dados de item

    CallPinchHP,

    // dados de status

    CallStatusTimerDec,

    // dados de campo

    CallWeather,
    CallWeatherSelf,
    CallFieldTimerDec,

    // outros

    CallSTAB,


/* AÇÕES */

    // ativação da habilidade

    AfterActivation,

    // seleção de ação

    BlockSwitch,

    // antes de movimento

    StartMessage,
    UseMove,
    BeforeHit,

    // depois de movimento

    HitTarget,
    HitUser,
    PPConsumption,

    AnyMoveSuccess,

    // fim do turno

    TurnEnd,

    // troca de Pokémon

    SwitchOut,
    Entry,

    // faint

    AnyFaint,
    FaintTarget,
    FaintUser,

    // impedimento

    TryCritUser,
    TryDamage,
    TryFieldCountDown,
    TryForceSwitch,
    TryHitUser,
    TryHitUserTest,
    TryIntimidate,
    TryRemoveItem,
    TrySelectMove,
    TryStatChangeOnUser,
    TryStatusConditionOnUser,
    TryUseMove,
    AfterBlockMove,

    OpponentTryProtect,
    OpponentTrySwitch,
    OpponentTryUseBerry,

    // garantia de acerto

    HitGuarantee,
    OpponentHitGuarantee,

    // movimento
    
    SecondaryEffectActivation,

    OpponentSecondaryEffectActivation,

    // dano

    Crit,
    DeductHP,
    PostHitMessage,

    // campo

    WeatherChange,
    TerrainChange,


/* EFEITOS */

    Flinch,
    IgnoreAbility,
    Intimidated,
    ItemConsumed,
    ItemGained,
    ModifyBerryEffect,
    ModifyStatChangeStages,
    StatChangeOnUser,
    StatusConditionOnTarget,
    StatusConditionOnUser,

    
/* ALTERAÇÕES DE ESTADO DA HABILIDADE */

    AbilityUpdate,
    Removed
}