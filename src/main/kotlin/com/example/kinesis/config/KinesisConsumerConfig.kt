package com.example.kinesis.config

import com.example.kinesis.consumer.StockTradeProcessorFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient
import software.amazon.kinesis.common.ConfigsBuilder
import software.amazon.kinesis.common.InitialPositionInStream
import software.amazon.kinesis.common.InitialPositionInStreamExtended
import software.amazon.kinesis.coordinator.Scheduler
import software.amazon.kinesis.retrieval.polling.PollingConfig
import java.util.*


@Configuration
class KinesisConsumerConfig(
    private val awsProperties: AwsProperties,
    private val kinesisAsyncClient: KinesisAsyncClient,
) {
    private val workerId = "merchant-worker"
    private val region = Region.of(awsProperties.region)

    @Bean
    fun consumerScheduler(): Scheduler {

        val dynamoDbAsyncClient = DynamoDbAsyncClient.builder()
            .region(region)
            .build()

        val cloudWatchClient = CloudWatchAsyncClient.builder()
            .region(region)
            .build()

        val shardRecordProcessor = StockTradeProcessorFactory()

        val configsBuilder = ConfigsBuilder(
            awsProperties.streamName,
            workerId,
            kinesisAsyncClient,
            dynamoDbAsyncClient,
            cloudWatchClient,
            UUID.randomUUID().toString(),
            shardRecordProcessor
        )

        // horizon configuration.
        val initialPositionInStreamExtended =
            InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON)
        val retrievalConfig =
            configsBuilder.retrievalConfig()
                .retrievalSpecificConfig(PollingConfig(awsProperties.streamName, kinesisAsyncClient))
        retrievalConfig.initialPositionInStreamExtended(initialPositionInStreamExtended)

        return Scheduler(
            configsBuilder.checkpointConfig(),
            configsBuilder.coordinatorConfig(),
            configsBuilder.leaseManagementConfig(),
            configsBuilder.lifecycleConfig(),
            configsBuilder.metricsConfig(),
            configsBuilder.processorConfig(),
            retrievalConfig
        )

    }

}