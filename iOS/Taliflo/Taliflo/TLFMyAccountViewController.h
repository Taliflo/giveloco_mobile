//
//  TLFMyAccountViewController.h
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TLFMyAccountViewController : UIViewController <UITableViewDataSource, UITableViewDelegate, UIActionSheetDelegate>

@property (strong, nonatomic) IBOutlet UILabel *name;
@property (strong, nonatomic) IBOutlet UILabel *balance;
@property (strong, nonatomic) IBOutlet UITableView *tableView;

@end
