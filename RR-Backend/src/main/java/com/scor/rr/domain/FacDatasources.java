package com.scor.rr.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class FacDatasources {

    @Id
    @Column(name = "database_id")
    private Integer databaseId;
    @Column(name = "name")
    private String name;
    @Column(name = "source_database_id")
    private Integer sourceDatabaseId;
    @Column(name = "owner_sid")
    private byte[] ownerSid;
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "compatibility_level")
    private byte compatibilityLevel;
    @Column(name = "collation_name")
    private String collationName;
    @Column(name = "user_access")
    private Byte userAccess;
    @Column(name = "user_access_desc")
    private String userAccessDesc;
    @Column(name = "is_read_only")
    private Boolean isReadOnly;
    @Column(name = "is_auto_close_on")
    private boolean isAutoCloseOn;
    @Column(name = "is_auto_shrink_on")
    private Boolean isAutoShrinkOn;
    @Column(name = "state")
    private Byte state;
    @Column(name = "state_desc")
    private String stateDesc;
    @Column(name = "is_in_standby")
    private Boolean isInStandby;
    @Column(name = "is_cleanly_shutdown")
    private Boolean isCleanlyShutdown;
    @Column(name = "is_supplemental_logging_enabled")
    private Boolean isSupplementalLoggingEnabled;
    @Column(name = "snapshot_isolation_state")
    private Byte snapshotIsolationState;
    @Column(name = "snapshot_isolation_state_desc")
    private String snapshotIsolationStateDesc;
    @Column(name = "is_read_committed_snapshot_on")
    private Boolean isReadCommittedSnapshotOn;
    @Column(name = "recovery_model")
    private Byte recoveryModel;
    @Column(name = "recovery_model_desc")
    private String recoveryModelDesc;
    @Column(name = "page_verify_option")
    private Byte pageVerifyOption;
    @Column(name = "page_verify_option_desc")
    private String pageVerifyOptionDesc;
    @Column(name = "is_auto_create_stats_on")
    private Boolean isAutoCreateStatsOn;
    @Column(name = "is_auto_create_stats_incremental_on")
    private Boolean isAutoCreateStatsIncrementalOn;
    @Column(name = "is_auto_update_stats_on")
    private Boolean isAutoUpdateStatsOn;
    @Column(name = "is_auto_update_stats_async_on")
    private Boolean isAutoUpdateStatsAsyncOn;
    @Column(name = "is_ansi_null_default_on")
    private Boolean isAnsiNullDefaultOn;
    @Column(name = "is_ansi_nulls_on")
    private Boolean isAnsiNullsOn;
    @Column(name = "is_ansi_padding_on")
    private Boolean isAnsiPaddingOn;
    @Column(name = "is_ansi_warnings_on")
    private Boolean isAnsiWarningsOn;
    @Column(name = "is_arithabort_on")
    private Boolean isArithabortOn;
    @Column(name = "is_concat_null_yields_null_on")
    private Boolean isConcatNullYieldsNullOn;
    @Column(name = "is_numeric_roundabort_on")
    private Boolean isNumericRoundabortOn;
    @Column(name = "is_quoted_identifier_on")
    private Boolean isQuotedIdentifierOn;
    @Column(name = "is_recursive_triggers_on")
    private Boolean isRecursiveTriggersOn;
    @Column(name = "is_cursor_close_on_commit_on")
    private Boolean isCursorCloseOnCommitOn;
    @Column(name = "is_local_cursor_default")
    private Boolean isLocalCursorDefault;
    @Column(name = "is_fulltext_enabled")
    private Boolean isFulltextEnabled;
    @Column(name = "is_trustworthy_on")
    private Boolean isTrustworthyOn;
    @Column(name = "is_db_chaining_on")
    private Boolean isDbChainingOn;
    @Column(name = "is_parameterization_forced")
    private Boolean isParameterizationForced;
    @Column(name = "is_master_key_encrypted_by_server")
    private boolean isMasterKeyEncryptedByServer;
    @Column(name = "is_query_store_on")
    private Boolean isQueryStoreOn;
    @Column(name = "is_published")
    private boolean isPublished;
    @Column(name = "is_subscribed")
    private boolean isSubscribed;
    @Column(name = "is_merge_published")
    private boolean isMergePublished;
    @Column(name = "is_distributor")
    private boolean isDistributor;
    @Column(name = "is_sync_with_backup")
    private boolean isSyncWithBackup;
    @Column(name = "service_broker_guid")
    private String serviceBrokerGuid;
    @Column(name = "is_broker_enabled")
    private boolean isBrokerEnabled;
    @Column(name = "log_reuse_wait")
    private Byte logReuseWait;
    @Column(name = "log_reuse_wait_desc")
    private String logReuseWaitDesc;
    @Column(name = "is_date_correlation_on")
    private boolean isDateCorrelationOn;
    @Column(name = "is_cdc_enabled")
    private boolean isCdcEnabled;
    @Column(name = "is_encrypted")
    private Boolean isEncrypted;
    @Column(name = "is_honor_broker_priority_on")
    private Boolean isHonorBrokerPriorityOn;
    @Column(name = "replica_id")
    private String replicaId;
    @Column(name = "group_database_id")
    private String groupDatabaseId;
    @Column(name = "resource_pool_id")
    private Integer resourcePoolId;
    @Column(name = "default_language_lcid")
    private Short defaultLanguageLcid;
    @Column(name = "default_language_name")
    private String defaultLanguageName;
    @Column(name = "default_fulltext_language_lcid")
    private Integer defaultFulltextLanguageLcid;
    @Column(name = "default_fulltext_language_name")
    private String defaultFulltextLanguageName;
    @Column(name = "is_nested_triggers_on")
    private Boolean isNestedTriggersOn;
    @Column(name = "is_transform_noise_words_on")
    private Boolean isTransformNoiseWordsOn;
    @Column(name = "two_digit_year_cutoff")
    private Short twoDigitYearCutoff;
    @Column(name = "containment")
    private Byte containment;
    @Column(name = "containment_desc")
    private String containmentDesc;
    @Column(name = "target_recovery_time_in_seconds")
    private Integer targetRecoveryTimeInSeconds;
    @Column(name = "delayed_durability")
    private Integer delayedDurability;
    @Column(name = "delayed_durability_desc")
    private String delayedDurabilityDesc;
    @Column(name = "is_memory_optimized_elevate_to_snapshot_on")
    private Boolean isMemoryOptimizedElevateToSnapshotOn;
    @Column(name = "is_federation_member")
    private Boolean isFederationMember;
    @Column(name = "is_remote_data_archive_enabled")
    private Boolean isRemoteDataArchiveEnabled;
    @Column(name = "is_mixed_page_allocation_on")
    private Boolean isMixedPageAllocationOn;

}
