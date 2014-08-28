//
//  TLFRestAssistant.h
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TLFUser.h"


@interface TLFRestHelper : NSObject

@property (nonatomic, strong) NSMutableArray* users;
@property (nonatomic, strong) NSMutableArray* transactions;
@property (nonatomic, strong) TLFUser *user;
@property (nonatomic, weak) UITableView *tableView;

- (instancetype)initWithTableView:(UITableView *)tableView;
- (NSURL *)queryUsers;
- (NSURL *)queryUser:(int)numID;
- (NSURL *)queryTransactions;
- (void)requestUsers:(NSString *)role;

@end
