//
//  TLFAppDelegate.h
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TLFMyAccountViewController, TLFCausesViewController, TLFBusinessesViewController, TLFLoginViewController;

@interface TLFAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) TLFMyAccountViewController *myAccountVC;
@property (strong, nonatomic) TLFCausesViewController *causesVC;
@property (strong, nonatomic) TLFBusinessesViewController *businessesVC;
@property (strong, nonatomic) TLFLoginViewController *loginVC;
@property (strong, nonatomic) UINavigationController *myAccountNC, *causesNC, *businessesNC;
@property (strong, nonatomic) UITabBarController *tabBarController;

- (void)startApplicationAfterLogin;
- (void)restartApplication;

@end
