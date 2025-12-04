package data.activationConditions;

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

    // dados de campo

    CallWeather,

    // outros

    CallSTAB,


/* AÇÕES */

    // seleção de ação

    BlockSwitch,

    // antes de movimento

    UseMove,
    BeforeHit,

    // depois de movimento

    HitTarget,
    HitUser,
    PPConsumption,

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
    TryFieldCountDown,
    TryForceSwitch,
    TryHitUser,
    TryIndirectDamage,
    TryIntimidate,
    TryStatChangeOnUser,
    TryStatusConditionOnUser,
    TryUseMove,

    OpponentTryProtect,
    OpponentTrySwitch,
    OpponentTryUseBerry,

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
    ModifyStatChangeStages,
    StatChangeOnUser,
    StatusConditionOnTarget,
    StatusConditionOnUser,

    
/* ALTERAÇÕES DE ESTADO DA HABILIDADE */

    AbilityUpdate,
    Removed,
}