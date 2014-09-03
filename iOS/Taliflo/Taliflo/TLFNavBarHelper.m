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

@implementation TLFNavBarHelper

+ (instancetype)getInstance
{
    static TLFNavBarHelper *sharedInstance = nil;
    static dispatch_once_t pred;
    
    if (sharedInstance) return sharedInstance;
    
    dispatch_once(&pred, ^{
        sharedInstance = [TLFNavBarHelper alloc];
        sharedInstance = [sharedInstance initPrivate];
    });
    
    return sharedInstance;
}

// If a prgrammer calls [[TLFNavBarHelper]], let him know the error of his ways
- (instancetype)init
{
    @throw [NSException exceptionWithName:@"Singleton"
                                   reason:@"Use +[TLFNavBarHelper getInstance]"
                                 userInfo:nil];
}

// Here is the real (secret) initializer
- (instancetype)initPrivate
{
    self = [super init];
    
    return self;
}

- (void)configViewController:(UIViewController *)viewController withTitle:(NSString *)title
{
    // Set the navigation bar title
    UINavigationItem *navItem = viewController.navigationItem;
    navItem.title = title;
    navItem.backBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"" style:UIBarButtonItemStylePlain target:nil action:nil];
    
    /*[navItem.backBarButtonItem setTitleTextAttributes:@{NSFontAttributeName:[UIFont systemFontOfSize:10.0]} forState:UIControlStateNormal]; */
}

- (void)configViewController:(UIViewController *)viewController withTitle:(NSString *)title withImage:(UIImage *)image
{    
    // Set the navigation bar title
    [self configViewController:viewController withTitle:title];
    
    // Set search as the right bar button
    viewController.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemSearch
                                                                               target:viewController
                                                                               action:nil];
    
    
    // Set the tab bar title and image
    viewController.tabBarItem.title = title;
    viewController.tabBarItem.image = image;
}

- (void)configViewController:(UIViewController *)viewController
             withBarTintColor:(UIColor *)barTintColor
                withTintColor:(UIColor *)tintColor
{
    // Set the navigation bar style
    UINavigationBar *navBar = viewController.navigationController.navigationBar;
    navBar.barTintColor = barTintColor;
    navBar.tintColor = tintColor;
    [navBar setTitleTextAttributes:@{NSForegroundColorAttributeName : tintColor}];
}

@end
