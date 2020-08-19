#import "BaiduadPlugin.h"
#if __has_include(<baiduad/baiduad-Swift.h>)
#import <baiduad/baiduad-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "baiduad-Swift.h"
#endif

@implementation BaiduadPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftBaiduadPlugin registerWithRegistrar:registrar];
}
@end
