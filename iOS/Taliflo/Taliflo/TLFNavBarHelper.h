//
//  TLFNavBarConfig.h
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "REMenu.h"

@interface TLFNavBarHelper : NSObject <UIActionSheetDelegate>

@property (nonatomic, strong, readwrite) REMenu *actionMenu;
@property (nonatomic, strong) UIViewController *viewController;

- (instancetype)initWithViewController:(UIViewController *)viewController;

- (instancetype)initWithViewController:(UIViewController *)viewController title:(NSString *)title;

+ (void)configViewController:(UIViewController *)viewController
                    withTabBarTitle:(NSString *)title
                    withIcon:(UIImage *)image;

+ (void)configViewController:(UIViewController *)viewController
             withBarTintColor:(UIColor *)barTintColor
                withTintColor:(UIColor *)tintColor;

@end
