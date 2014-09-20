//
//  TLFNavBarConfig.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFNavBarHelper.h"
#import "TLFColor.h"
#import "TLFBillingInfoViewController.h"
#import "TLFRedeemCreditsViewController.h"
#import "TLFRestHelper.h"
#import "TLFAlert.h"
#import "TLFUserStore.h"
#import "AFNetworking.h"
#import "TLFAppDelegate.h"

@interface TLFNavBarHelper ()

@end

@implementation TLFNavBarHelper

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
                                                                   TLFRedeemCreditsViewController *redeemCreditsVC = [[TLFRedeemCreditsViewController alloc] init];
                                                                   [self.viewController.navigationController pushViewController:redeemCreditsVC animated:YES];
                                                               }];
        
        REMenuItem *billingInfo = [[REMenuItem alloc] initWithTitle:@"Update Billing Info"
                                                              image:[UIImage imageNamed:@"UpdateBillingInfo"]
                                                   highlightedImage:nil
                                                             action:^(REMenuItem *item) {
                                                                 NSLog(@"'Update Billing Info' clicked");
                                                                 TLFBillingInfoViewController *billingInfoVC = [[TLFBillingInfoViewController alloc] init];
                                                                 [self.viewController.navigationController pushViewController:billingInfoVC animated:YES];
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
                                                            
                                                            TLFUserStore *userStore = [TLFUserStore getInstance];
                                                            
                                                            AFHTTPSessionManager *manager = [TLFRestHelper newSessionManager:userStore.authToken];
                                                            
                                                            [manager DELETE:@"http://api-dev.taliflo.com/user/logout" parameters:nil success:^(NSURLSessionDataTask *task, id responseObject) {
                                                                NSLog(@"%@", responseObject);
                                                                
                                                                // Restart application
                                                                TLFAppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
                                                                [appDelegate restartApplication];
                                                                
                                                            } failure:^(NSURLSessionDataTask *task, NSError *error) {
                                                                NSLog(@"%@", [error localizedDescription]);
                                                                
                                                                // Show alert
                                                                [TLFAlert alertForViewController:self.viewController forError:error withTitle:@"Logout Error"];
                
                                                            }];
                                                            
                                                        }];
        
        self.actionMenu = [[REMenu alloc] initWithItems:@[redeemCredits, billingInfo, logout] viewController:self.viewController];
        self.actionMenu.backgroundColor = [TLFColor colorWithHexString:@"e8e8e8"];
        self.actionMenu.borderColor = [TLFColor mediumGrey];
        self.actionMenu.textColor = [TLFColor talifloTiffanyBlue];
        self.actionMenu.borderWidth = .5;
        self.actionMenu.separatorHeight = .5;
        self.actionMenu.separatorColor = [TLFColor mediumGrey];
        self.actionMenu.highlightedBackgroundColor = [TLFColor mediumGrey];
        self.actionMenu.highlightedTextColor = [TLFColor talifloTiffanyBlue];
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

@end
