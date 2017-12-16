//
//  TLFColor.h
//  taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CustomColor : NSObject

+ (UIColor *)colorWithHexString:(NSString *)hex;
+ (UIColor *)talifloTiffanyBlue;
+ (UIColor *)talifloOrange;
+ (UIColor *)talifloPurple;
+ (UIColor *)lightGrey;
+ (UIColor *)mediumGrey;
+ (UIColor *)disabledGrey;
+ (UIColor *)lightestGrey;
+ (void)setStrokeTB:(UIView *)view;
@end
