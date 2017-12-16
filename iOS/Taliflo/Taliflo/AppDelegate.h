//
//  TLFAppDelegate.h
//  taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MyAccountViewController, CausesViewController, BusinessesViewController, LoginViewController;

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;

@property (strong, nonatomic) MyAccountViewController *myAccountVC;
@property (strong, nonatomic) CausesViewController *causesVC;
@property (strong, nonatomic) BusinessesViewController *businessesVC;
@property (strong, nonatomic) LoginViewController *loginVC;
@property (strong, nonatomic) UINavigationController *myAccountNC, *causesNC, *businessesNC;
@property (strong, nonatomic) UITabBarController *tabBarController;

- (void)startApplication;
- (void)restartApplication;

@end
