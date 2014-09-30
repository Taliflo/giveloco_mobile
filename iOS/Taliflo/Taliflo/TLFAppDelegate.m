//
//  TLFAppDelegate.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#define k_loggedIn @"loggedIn"
#define k_authToken @"authToken"
#define k_uid @"uid"

#define TestPublishableKey @"pk_test_MBnCYXpbwBkIjjjpIKz6lSud"
#define LivePublishableKey @"pk_live_HGECFVdMKGneyKozG9g2Fnxn"

#import "TLFAppDelegate.h"
#import <AFNetworking/AFNetworkActivityIndicatorManager.h>
#import "TLFColor.h"
#import "TLFMyAccountViewController.h"
#import "TLFCausesViewController.h"
#import "TLFBusinessesViewController.h"
#import "TLFLoginViewController.h"
#import "TLFSignupViewController.h"
#import "TLFUserStore.h"
#import "Stripe.h"

@implementation TLFAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    
    [Stripe setDefaultPublishableKey:TestPublishableKey];
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    
    // Show activity indicator
    [AFNetworkActivityIndicatorManager sharedManager].enabled = YES;
    
    // Register view controller for testing
/*    TLFRegisterViewController * registerVC = [[TLFRegisterViewController alloc] init];
    self.window.rootViewController = registerVC; */
    
    // Retrieve saved credentials if present
    NSUserDefaults *savedCredentials = [NSUserDefaults standardUserDefaults];
    BOOL loggedIn = [savedCredentials boolForKey:k_loggedIn];
    
    if (loggedIn) {
        TLFUserStore *userStore = [TLFUserStore getInstance];
        userStore.authToken = [savedCredentials objectForKey:k_authToken];
        userStore.uid = [savedCredentials objectForKey:k_uid];
        [self startApplication];
    } else {
    // Login view controller
    self.loginVC = [[TLFLoginViewController alloc] init];
    self.window.rootViewController = self.loginVC;
    }
    
    self.window.backgroundColor = [TLFColor lightestGrey];
    [self.window makeKeyAndVisible];
    
    return YES;
}

- (void)startApplication
{
    // My Account view controller and navigation controller
    self.myAccountVC = [[TLFMyAccountViewController alloc] init];
    self.myAccountNC = [[UINavigationController alloc] initWithRootViewController:self.myAccountVC];
    
    // Causes table view controller and navigation controller
    self.causesVC = [[TLFCausesViewController alloc] init];
    self.causesNC = [[UINavigationController alloc] initWithRootViewController:self.causesVC];
    
    // Businesses table view controller and navigation controller
    self.businessesVC = [[TLFBusinessesViewController alloc] init];
    self.businessesNC = [[UINavigationController alloc] initWithRootViewController:self.businessesVC];
    
    // Tab bar controller
    self.tabBarController = [[UITabBarController alloc] init];
    self.tabBarController.viewControllers = @[self.myAccountNC, self.causesNC, self.businessesNC];
    [self.tabBarController.tabBar setBarTintColor:[TLFColor lightestGrey]];
    [self.tabBarController.tabBar setTintColor:[TLFColor talifloTiffanyBlue]];
    
    // Set the navigation bar style
    [[UINavigationBar appearance] setBarTintColor:[UIColor whiteColor]];
    [[UINavigationBar appearance] setTintColor:[TLFColor talifloTiffanyBlue]];
    [[UINavigationBar appearance] setTitleTextAttributes:@{NSForegroundColorAttributeName : [UIColor blackColor]}];
    
    // Setting navigation bar back button font size
    [[UIBarButtonItem appearanceWhenContainedIn:[UINavigationBar class], nil] setTitleTextAttributes:@{NSFontAttributeName: [UIFont boldSystemFontOfSize:14.0]} forState:UIControlStateNormal];
    
    self.window.rootViewController = self.tabBarController;
    
    self.loginVC = nil;
}

- (void)restartApplication
{
    
    [UIView transitionWithView:self.window
                      duration:0.5
                       options:UIViewAnimationOptionTransitionFlipFromLeft
                    animations:^{
                        self.loginVC = [[TLFLoginViewController alloc] init];
                        self.window.rootViewController = self.loginVC;
                    }
                    completion:^(BOOL finished) {
                        
                        self.myAccountVC = nil;
                        self.myAccountNC = nil;
                        
                        self.causesVC = nil;
                        self.causesNC = nil;
                        
                        self.businessesVC = nil;
                        self.businessesNC = nil;
                        
                        self.tabBarController = nil;
                    }];
}

// Lock portrait orientation
- (NSUInteger)application:(UIApplication *)application supportedInterfaceOrientationsForWindow:(UIWindow *)window
{
    return UIInterfaceOrientationMaskPortrait;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
