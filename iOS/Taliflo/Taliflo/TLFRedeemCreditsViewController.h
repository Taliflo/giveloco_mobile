//
//  TLFRedeemCreditsViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TLFUser;

@interface TLFRedeemCreditsViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UILabel *noneMsg;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic) TLFUser *currentUser;

@end
