//
//  TLFNavBarConfig.m
//  taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "NavigationBarHelper.h"
#import "CustomColor.h"
#import "BillingInfoViewController.h"
#import "RedeemCreditsViewController.h"
#import "NetworkHelper.h"
#import "Alert.h"
#import "UserStore.h"
#import "AppDelegate.h"

@interface NavigationBarHelper ()

@end

@implementation NavigationBarHelper

- (instancetype)initWithViewController:(UIViewController *)viewController
{
    self = [super init];
    if (self) {
        self.viewController = viewController;
        UINavigationItem *navItem = self.viewController.navigationItem;
        navItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
        
        navItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"More"]
                                                                      style:UIBarButtonItemStylePlain
                                                                     target:self
                                                                     action:@selector(toggleActionMenu)];
        
        // Set up the action menu
        
        REMenuItem *redeemCredits = [[REMenuItem alloc] initWithTitle:@"Redeem Credits"
                                                                image:[UIImage imageNamed:@"RedeemCredits"]
                                                     highlightedImage:nil
                                                               action:^(REMenuItem *item) {
                                                                   NSLog(@"'Redeem Credits' clicked");
                                                                   RedeemCreditsViewController *redeemCreditsVC = [[RedeemCreditsViewController alloc] init];
                                                                   [self presentModalViewController:redeemCreditsVC];
                                                               }];
        
        REMenuItem *billingInfo = [[REMenuItem alloc] initWithTitle:@"Update Billing Info"
                                                              image:[UIImage imageNamed:@"UpdateBillingInfo"]
                                                   highlightedImage:nil
                                                             action:^(REMenuItem *item) {
                                                                 NSLog(@"'Update Billing Info' clicked");
                                                                 BillingInfoViewController *billingInfoVC = [[BillingInfoViewController alloc] init];
                                                                 [self presentModalViewController:billingInfoVC];
                                                             }];
        /*
         REMenuItem *locate = [[REMenuItem alloc] initWithTitle:@"Locate"
         image:[UIImage imageNamed:@"Explore"]
         highlightedImage:nil
         action:^(REMenuItem *item) {
         NSLog(@"'Locate' clicked");
         }];*/
        
        REMenuItem *logout = [[REMenuItem alloc] initWithTitle:@"Logout"
                                                         image:[UIImage imageNamed:@"Logout"]
                                              highlightedImage:nil
                                                        action:^(REMenuItem *item) {
                                                            NSLog(@"'Logout' clicked");
                                                            
                                                            /* Just for now. Come back and sort out the whole logging out issue. */
                                                            
                                                            // Delete saved credentials
                                                            [[NSUserDefaults standardUserDefaults] removePersistentDomainForName:@"com.taliflo.taliflo"];
                                                            [[NSUserDefaults standardUserDefaults] synchronize];
                                                            
                                                            // Restart application
                                                            AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
                                                            [appDelegate restartApplication];
                                                            
                                                            UserStore *userStore = [UserStore getInstance];
                                                            AFHTTPSessionManager *manager = [NetworkHelper newSessionManager:userStore.authToken];
                                                            [manager DELETE:@"user/logout"
                                                                 parameters:nil
                                                                    success:^(NSURLSessionDataTask *task, id responseObject) {
                                                                NSLog(@"%@", responseObject);
                                                                        /*
                                                                        // Delete saved credentials
                                                                        [[NSUserDefaults standardUserDefaults] removePersistentDomainForName:@"com.taliflo.taliflo"];
                                                                        [[NSUserDefaults standardUserDefaults] synchronize];
                                                                        
                                                                
                                                                        // Restart application
                                                                        TLFAppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
                                                                        [appDelegate restartApplication];
                                                                         */
                                                                
                                                            } failure:^(NSURLSessionDataTask *task, NSError *error) {
                                                                //NSLog(@"%@", [error localizedDescription]);
                                                                NSLog(@"%@", error);
                                                                // Show alert
                                                                [Alert alertForViewController:self.viewController forError:error withTitle:@"Logout Error"];
                
                                                            }];
                                                        }];
        
        self.actionMenu = [[REMenu alloc] initWithItems:@[redeemCredits, billingInfo, logout] viewController:self.viewController];
        self.actionMenu.backgroundColor = [CustomColor colorWithHexString:@"e8e8e8"];
        self.actionMenu.borderColor = [CustomColor mediumGrey];
        self.actionMenu.textColor = [CustomColor talifloTiffanyBlue];
        self.actionMenu.borderWidth = .5;
        self.actionMenu.separatorHeight = .5;
        self.actionMenu.separatorColor = [CustomColor mediumGrey];
        self.actionMenu.highlightedBackgroundColor = [CustomColor mediumGrey];
        self.actionMenu.highlightedTextColor = [CustomColor talifloTiffanyBlue];
        self.actionMenu.highlightedTextShadowColor = nil;
        self.actionMenu.shadowColor = [UIColor blackColor];
        self.actionMenu.shadowOpacity = 0.7;
        self.actionMenu.shadowOffset = CGSizeMake(320, 565);
        self.actionMenu.font = [UIFont systemFontOfSize:17.0];
        self.actionMenu.bounce = NO;
    }
    return self;
}

- (instancetype)initWithViewController:(UIViewController *)viewController title:(NSString *)title
{
    self = [self initWithViewController:viewController];
    if (self) {
        self.viewController.title = title;
    }
    return self;
}

- (void)toggleActionMenu
{
    if (self.actionMenu.isOpen) {
        [self.actionMenu close];
        return;
    }
    
    [self.actionMenu showFromNavigationController:self.viewController.navigationController];
}

+ (void)configViewController:(UIViewController *)viewController withTabBarTitle:(NSString *)title withIcon:(UIImage *)image
{
    // Set the tab bar title and image
    viewController.tabBarItem.title = title;
    viewController.tabBarItem.image = image;
}

+ (void)configViewController:(UIViewController *)viewController
             withBarTintColor:(UIColor *)barTintColor
                withTintColor:(UIColor *)tintColor
{
    // Set the navigation bar style
    UINavigationBar *navBar = viewController.navigationController.navigationBar;
    navBar.barTintColor = barTintColor;
    navBar.tintColor = tintColor;
    [navBar setTitleTextAttributes:@{NSForegroundColorAttributeName : [UIColor blackColor]}];
}

- (void)presentModalViewController:(UIViewController *)presentedViewController
{
    presentedViewController.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemCancel target:self action:@selector(dismissModalViewController)];
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:presentedViewController];
    [self.viewController presentViewController:navigationController animated:YES completion:nil];
}

- (void)dismissModalViewController
{
    [self.viewController dismissViewControllerAnimated:YES completion:nil];
}

@end
