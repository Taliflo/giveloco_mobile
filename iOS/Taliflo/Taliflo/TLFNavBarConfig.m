//
//  TLFNavBarConfig.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFNavBarConfig.h"
#import "TLFColor.h"

@implementation TLFNavBarConfig

+ (void) configViewController:(UIViewController *)viewController withTitle:(NSString *)title withImage:(UIImage *)image
{    
    // Set the navigation bar title
    UINavigationItem *navItem = viewController.navigationItem;
    navItem.title = title;
    
    navItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"logo.png"]
                                                                 style:UIBarButtonItemStylePlain
                                                                target:viewController
                                                                action:nil];
    
    navItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemSearch
                                                                               target:viewController
                                                                               action:nil];
    
    
    // Set the tab bar title and image
    viewController.tabBarItem.title = title;
    viewController.tabBarItem.image = image;
}

+ (void) configViewController:(UIViewController *)viewController
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
