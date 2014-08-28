//
//  TLFMyAccountViewController.m
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>

#import "TLFMyAccountViewController.h"
#import "TLFNavBarHelper.h"
#import "TLFColor.h"
#import "TLFRestHelper.h"
#import "AFNetworking.h"
#import "TLFUserStore.h"
#import "TLFTransactionCell.h"
#import "TLFTransaction.h"

@interface TLFMyAccountViewController ()

@property (nonatomic, strong) TLFUser *user;
@property (nonatomic, strong) NSMutableArray *transactions;

@end

static TLFNavBarHelper *helper;
static TLFRestHelper *restHelper;

@implementation TLFMyAccountViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        // Setting the nav bar title, and the tab bar title and image
        helper = [TLFNavBarHelper getInstance];
        [helper configViewController:self withTitle:@"My Account" withImage:[UIImage imageNamed:@"MyAccount.png"]];
        
        restHelper = [[TLFRestHelper alloc] init];
        [self requestUser:5];
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the nav bar style
    [helper configViewController:self withBarTintColor:[UIColor whiteColor] withTintColor:[TLFColor talifloTiffanyBlue]];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Styling
    [[_balance layer] setBorderColor:[[TLFColor talifloTiffanyBlue] CGColor]];
    [[_balance layer] setBorderWidth:2.0];
    [[_balance layer] setCornerRadius:3];
    CGRect frame = _balance.frame;
    frame.size.height = _balance.frame.size.height + 10;
    _balance.frame = frame;
    
    // Load the cell nib file
    UINib *nib = [UINib nibWithNibName:@"TLFTransactionCell" bundle:nil];
    // Register the nib file which contains the cell
    [self.tableView registerNib:nib forCellReuseIdentifier:@"TLFTransactionCell"];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)requestUser:(int)numID
{
    NSURLRequest *request = [NSURLRequest requestWithURL:[restHelper queryUser:numID]];
    
    // AFNetworking asynchronous URL request
    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc] initWithRequest:request];
    operation.responseSerializer = [AFJSONResponseSerializer serializer];
    [operation setCompletionBlockWithSuccess:
     ^(AFHTTPRequestOperation *operation, id responseObject) {
         _user = [[TLFUser alloc] initWithDictionary:responseObject];
         
         NSLog(@"USER NAME: %@", _user.companyName);
         dispatch_async(dispatch_get_main_queue(),
                        ^{
                            // Set user in global store
                            TLFUserStore *userStore = [TLFUserStore getInstance];
                            userStore.currentUser = _user;
                            
                            // Populate layout views
                            _name.text = _user.companyName;
                            _balance.text = [NSString stringWithFormat:@"%@", _user.balance];
                            
                            [_tableView reloadData];
                        }
                        );
     }
                                     failure:
     ^(AFHTTPRequestOperation *operation, NSError *error) {
         // Handle error
         NSLog(@"Request Failed: %@, %@", error, error.userInfo);
     }
     ];
    
    [operation start];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_user.transactions count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 80.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdent = @"TLFTransactionCell";
    
    TLFTransactionCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdent];
    
    if (!cell) {
        cell = [[TLFTransactionCell alloc] init];
    }
/*
    // Populate cell views (Transactions created)
    NSDictionary *trans = _user.transactionsCreated[indexPath.row];
    cell.type.text = trans[@"trans_type"];
    //cell.date.text = [trans formatCreatedAt];
    cell.party.text = trans[@"to_name"];
    //cell.amount.text = trans.amount;
    //cell.transID.text = trans.transID;
*/
    TLFTransaction *trans = [[TLFTransaction alloc] initWithDictionary:_user.transactions[indexPath.row]];
    cell.party.text = trans.toName;
    cell.date.text = [trans formatCreatedAt];
    cell.type.text = trans.transType;
    cell.amount.text = [NSString stringWithFormat:@"C %@", trans.amount];
    cell.transID.text = [NSString stringWithFormat:@"%@", trans.transID];
    
    if (indexPath.row % 2 == 0)
        cell.backgroundColor = [TLFColor colorWithHexString:@"eeeeee"];
    else
        cell.backgroundColor = [TLFColor colorWithHexString:@"fefefe"];
    
    return cell;
}

@end
