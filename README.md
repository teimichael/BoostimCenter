# BoostimCenter
The center node of Boostim, a set of solutions for instant messaging.

Center node controlls the overall logic including: access control by [BoostimAuth](https://github.com/teimichael/BoostimAuth), common nodes (workers) by [BoostimNode](https://github.com/teimichael/BoostimNode).

*TestWebClient* is a demo to test the functionality of Boostim through the center node.

## Quick Start (Recommended)
Quick start allows container-based deployment for the complete project. \
Please refer to [BoostimQuickStart](https://github.com/teimichael/BoostimQuickStart)

## For Development (IDE Recommended)
### Compile
```
mvn clean package -D maven.test.skip=true -P dev
```

### Start up
```
java -jar *
```

### Information
- **Default port**: 9000
- **API Doc**: /swagger-ui.html

## Related Repositories
- [BoostimQuickStart](https://github.com/teimichael/BoostimQuickStart): container-based deployment
- [BoostimCenter](https://github.com/teimichael/BoostimCenter): center node (controller)
- [BoostimNode](https://github.com/teimichael/BoostimNode): common node (worker)
- [BoostimAuth](https://github.com/teimichael/BoostimAuth): authentication system
- [BoostimWebClient](https://github.com/teimichael/BoostimWebClient): easy-integration web client