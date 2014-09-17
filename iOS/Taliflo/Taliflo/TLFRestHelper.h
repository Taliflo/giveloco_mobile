//
//  TLFRestAssistant.h
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TLFUser.h"

@class TLFCauseStore, TLFBusinessStore, AFHTTPSessionManager;

@interface TLFRestHelper : NSObject

@property (nonatomic, copy) NSMutableArray* users;
@property (nonatomic, copy) NSMutableArray* transactions;
@property (nonatomic, copy) TLFUser *user;
@property (nonatomic) UITableView *tableView;
@property (nonatomic) UITableViewController *viewController;

- (instancetype)initWithTableViewController:(UITableViewController *)tableViewController;
- (NSURL *)queryUsers;
- (NSURL *)queryUser:(int)numID;
- (NSURL *)queryTransactions;
- (void)requestUsers:(NSString *)role;

+ (AFHTTPSessionManager *)newSessionManager:(NSString *)authToken;
+ (void)showErrorAlertView:(NSError *)error withMessage:(NSString *)message;
@end
