//
//  TLFNavBarConfig.h
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFNavBarConfig : NSObject

+ (void) configViewController:(UIViewController *)viewController
                    withTitle:(NSString *)title
                    withImage:(UIImage *)image;

+ (void) configViewController:(UIViewController *)viewController
             withBarTintColor:(UIColor *)barTintColor
                withTintColor:(UIColor *)tintColor;

@end