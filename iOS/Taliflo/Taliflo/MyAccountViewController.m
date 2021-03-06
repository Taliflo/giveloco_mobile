//
//  TLFMyAccountViewController.m
//  taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import "MyAccountViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "User.h"
#import "NavigationBarHelper.h"
#import "CustomColor.h"
#import "NetworkHelper.h"
#import "UserStore.h"
#import "TransactionCell.h"
#import "Transaction.h"
#import "BillingInfoViewController.h"
#import "Alert.h"

@interface MyAccountViewController () {
    double startTime, endTime;
}

@property (nonatomic, strong) User *user;


@end

static NavigationBarHelper *nbHelper;
static NetworkHelper *networkHelper;
static UIView *indicatorView;

@implementation MyAccountViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
        // Setting the nav bar title, and the tab bar title and image
        //[TLFNavBarHelper configViewController:self withTitle:@"My Account" withImage:[UIImage imageNamed:@"MyAccount.png"]];
        
        nbHelper = [[NavigationBarHelper alloc] initWithViewController:self title:@"My Account"];
        [NavigationBarHelper configViewController:self
                              withTabBarTitle:@"My Account"
                                     withIcon:[UIImage imageNamed:@"MyAccount.png"]];
        
        // Request logged in user
        UserStore *userStore = [UserStore getInstance];
        networkHelper = [[NetworkHelper alloc] init];
        
        
        [self requestUser:userStore.uid];
        
        indicatorView = [[NSBundle mainBundle] loadNibNamed:@"ActivityIndicatorView" owner:self options:nil][0];
        [self.view addSubview:indicatorView];
    }
    return self;
}

- (IBAction)openBillingInfo:(id)sender
{
    BillingInfoViewController *billingInfoVC = [[BillingInfoViewController alloc] init];
    
    [self.navigationController pushViewController:billingInfoVC animated:YES];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    // Styling
    // Add top and bottom border to balance
    CGRect screenRect = [[UIScreen mainScreen] bounds];
    float borderHeight = 1.0;
    float borderWidth = screenRect.size.width;
    CALayer *topBorder = [CALayer layer];
    topBorder.backgroundColor = [[CustomColor talifloTiffanyBlue] CGColor];
    topBorder.frame = CGRectMake(0, 0, borderWidth, borderHeight);
    CALayer *bottomBorder = [CALayer layer];
    bottomBorder.backgroundColor = [[CustomColor talifloTiffanyBlue] CGColor];
    bottomBorder.frame = CGRectMake(0, 29, borderWidth, borderHeight);
    [self.balance.layer addSublayer:topBorder];
    [self.balance.layer addSublayer:bottomBorder];
    
    // Load the cell nib file
    UINib *nib = [UINib nibWithNibName:@"TLFTransactionCell" bundle:nil];
    // Register the nib file which contains the cell
    [self.tableView registerNib:nib forCellReuseIdentifier:@"TLFTransactionCell"];
    

}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    
    // Setting the tab bar selected item colour
    //self.tabBarController.tabBar.selectedImageTintColor = [TLFColor talifloOrange];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:YES];
    if (nbHelper.actionMenu.isOpen)
        [nbHelper.actionMenu close];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)requestUser:(NSString *)uid
{
    startTime = CACurrentMediaTime();
    
    void (^onSuccess)(NSURLSessionDataTask *operation, id responseObject);
    void (^onFailure)(NSURLSessionDataTask *operation, NSError *error);
    
    onSuccess = ^void(NSURLSessionDataTask *operation, id responseObject) {

        dispatch_async(dispatch_get_main_queue(),
                       ^{
                           self.user = [[User alloc] initWithJSON:responseObject];
                           // Set user in global store
                           UserStore *userStore = [UserStore getInstance];
                           userStore.currentUser = self.user;
                           // Populate layout views
                           self.name.text = [NSString stringWithFormat:@"%@ %@", self.user.firstName, self.user.lastName];

                           if ([self.balance isKindOfClass:[NSNull class]]) {
                               self.balance.text = [NSString stringWithFormat:@"C %@", self.user.balance];
                           } else {
                               self.balance.text = [NSString stringWithFormat:@"C %@", self.user.balance];
                           }
                           
                           
                           [self.tableView reloadData];
                           [indicatorView removeFromSuperview];
                           
                           endTime = CACurrentMediaTime();
                           
                           NSLog(@"Request logged in user execution time: %f sec", (endTime-startTime));

                       });
        

    };
    
    onFailure = ^void(NSURLSessionDataTask *operation, NSError *error) {
        NSLog(@"Request Failed: %@", [error localizedDescription]);
        
        // Show alert
        [Alert alertForViewController:self forError:error withTitle:@"Account Rerieval Error"];
        [indicatorView removeFromSuperview];
    };
    
    [NetworkHelper requestUser:uid successHandler:onSuccess failureHandler:onFailure];

}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.user.transactionsAll count];
    return 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 80.0f;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdent = @"TLFTransactionCell";
    
    TransactionCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdent];
    
    if (!cell) {
        cell = [[TransactionCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdent];
    }

    Transaction *trans = [[Transaction alloc] initWithJSON:self.user.transactionsAll[indexPath.row]];
    cell.party.text = trans.toName;
    cell.date.text = [trans formatCreatedAt];
    cell.type.text = trans.transType;
    cell.amount.text = [NSString stringWithFormat:@"C %@", trans.amount];
    cell.transID.text = [NSString stringWithFormat:@"%@", trans.transID];
    
    if (indexPath.row % 2 == 0)
        cell.backgroundColor = [CustomColor colorWithHexString:@"ffffff"];
    else
        cell.backgroundColor = [CustomColor colorWithHexString:@"eeeeee"];
    
    return cell;
}

@end
