//
//  TLFColor.h
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
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
