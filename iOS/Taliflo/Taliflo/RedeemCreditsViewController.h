//
//  TLFRedeemCreditsViewController.h
//  taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class User;

@interface RedeemCreditsViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UILabel *noneMsg;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic) User *currentUser;

@end
