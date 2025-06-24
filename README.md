# ApkDataMultiplex

CLI version of [ApkDataMultiplexing](https://github.com/L-JINBIN/ApkDataMultiplexing).

## Usage

```shell
java -jar ApkDataMultiplex.jar --help
Usage: ApkDataMultiplex [-hV] <inputApk> <outputApk> <baseApk>
Performs APK data multiplexing and signs the output.
      <inputApk>    Path to the input APK file
      <outputApk>   Path to the output APK file
      <baseApk>     Path to the duplicate base APK inside input APK path
  -h, --help        Show this help message and exit.
  -V, --version     Print version information and exit.
```

## Example

```shell
java -jar ApkDataMultiplex.jar test.apk out.apk assets/base.apk
Running optimization...
assets/base.apk >> offset=0x0
  +0x21ae  res/a
  +0x54ec  res/b
Data multiplexing optimize: test.apk (89.32KB) -> out.apk (67.79KB)  [-24.11%]
Optimization complete. Now signing...
Signing complete!
```
