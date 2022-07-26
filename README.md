# Spring boot + aws kinesis example source

[reference page](https://www.baeldung.com/spring-aws-kinesis)

[aws kinesis](https://docs.aws.amazon.com/ko_kr/streams/latest/dev/key-concepts.html)

## 1. gradle setting

전체적인 gradle setting을 작성했습니다. 자세한 정보는 source를 받아서 확인하시면 됩니다.

```
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.amazonaws:amazon-kinesis-client:1.14.8")
    implementation("com.amazonaws:amazon-kinesis-producer:0.14.12") {
        exclude(group = "commons-collections:commons-collections:3.2.2",)
    }

    implementation("org.apache.commons:commons-collections4:4.4")

    implementation("com.amazonaws:aws-java-sdk-sts:1.12.267")
    implementation("com.amazonaws:aws-java-sdk:1.12.267")

    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation("org.slf4j:slf4j-api:1.7.36")
```

version 2

https://docs.aws.amazon.com/ko_kr/sdk-for-java/latest/developer-guide/setup-project-gradle.html

```
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("software.amazon.kinesis:amazon-kinesis-client:2.4.1")

    implementation(platform("software.amazon.awssdk:bom:2.15.0"))
    implementation("software.amazon.awssdk:kinesis")
    implementation("software.amazon.awssdk:sts")
    implementation("software.amazon.awssdk:dynamodb")
    implementation("software.amazon.awssdk:cloudwatch")

    implementation("com.amazonaws:amazon-kinesis-producer:0.14.12") {
        exclude(group = "commons-collections:commons-collections:3.2.2")
    }

    implementation("org.apache.commons:commons-collections4:4.4")


    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    implementation("org.slf4j:slf4j-api:1.7.36")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
```

## 2. application.yml setting

아래 properties에 맞춰 정보를 입력한다.

```
aws:
  sts:
    region: REGION
    roleArn: ROLE
    sessionName: SESSION_NAME
    streamName: STREAM_NAME

```

roleArn은 임시 발급한 access key를 이용하여 사용할 경우, 별도의 role을 작성하여 사용하는 방법으로 정리 함.

consumer 설정이 아직은 확인 중이라 정리되는대로 다시 push 예정.

[spring cloud stream](https://spring.io/blog/2021/06/04/spring-integration-aws-2-5-1-and-spring-cloud-stream-kinesis-binder-2-2-0-available)
에서는 지원이 끊긴 것 같은(?) 상황이라 별도로 작성해 봄.

# IAM role 추가

kinesis role

```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt123",
            "Effect": "Allow",
            "Action": [
                "kinesis:DescribeStream",
                "kinesis:PutRecord",
                "kinesis:PutRecords",
                "kinesis:GetShardIterator",
                "kinesis:GetRecords",
                "kinesis:ListShards",
                "kinesis:DescribeStreamSummary",
                "kinesis:RegisterStreamConsumer"
            ],
            "Resource": [
                "arn:aws:kinesis:region위치:세션명:stream/kinesisStream명"
            ]
        }
    ]
}
```

DynamoDB

```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt234",
            "Effect": "Allow",
            "Action": [
                "kinesis:SubscribeToShard",
                "kinesis:DescribeStreamConsumer"
            ],
            "Resource": [
                "arn:aws:kinesis:region위치:세션명:stream/kinesisStream명/*"
            ]
        },
        {
            "Sid": "Stmt456",
            "Effect": "Allow",
            "Action": [
                "dynamodb:*"
            ],
            "Resource": [
                "*"
            ]
        },
        {
            "Sid": "Stmt789",
            "Effect": "Allow",
            "Action": [
                "cloudwatch:PutMetricData"
            ],
            "Resource": [
                "*"
            ]
        }
    ]
}
```

# 진행하면서 발생된 이슈들

v1, v2 문제

v2는 레퍼런스 문서나 라이브러리가 잘 정리되어 있지 않아 많이 헤멤.

com.amazonaws.sdk
software.amazonaws.sdk의 전쟁

### http client 이슈

https://github.com/aws/aws-sdk-java-v2/issues/446

### 종속 라이브러리 이슈

https://docs.aws.amazon.com/ko_kr/sdk-for-java/latest/developer-guide/setup-project-gradle.html


# 메시지 생성 방법

```
~\ curl http://localhost:8080
```