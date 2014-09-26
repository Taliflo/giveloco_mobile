//
//  TLFRestAssistant.m
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFNetworkHelper.h"
#import "TLFCauseStore.h"
#import "TLFBusinessStore.h"
#import "TLFUserStore.h"
#import "TLFAlert.h"
#import "TLFUser.h"

@interface TLFNetworkHelper () 

@end

@implementation TLFNetworkHelper

static double startTime, endTime;

+ (AFHTTPSessionManager *)newSessionManager:(NSString *)authToken
{
    NSURL *baseURL = [NSURL URLWithString:@"http://api-dev.taliflo.com/"];
    AFHTTPSessionManager *manager = [[AFHTTPSessionManager alloc] initWithBaseURL:baseURL];
    manager.requestSerializer = [AFJSONRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    
    if (authToken != nil) {
        [manager.requestSerializer setValue:authToken forHTTPHeaderField:@"X-Session-Token"];
    }
    
    return manager;
}

+ (void)requestUser:(NSString *)uid successHandler:(void (^)(NSURLSessionDataTask* task, id responseObject))onSuccess failureHandler:(void (^)(NSURLSessionDataTask* task, NSError *error))onFailure
{
    TLFUserStore *userStore = [TLFUserStore getInstance];
    AFHTTPSessionManager *manager = [TLFNetworkHelper newSessionManager:userStore.authToken];
    
    [manager GET:[NSString stringWithFormat:@"v1/users/%@", userStore.uid] parameters:nil success:onSuccess failure:onFailure];
}

+ (void)requestUsers:(NSString *)role forTableViewController:(UITableViewController *)viewController backingList:(NSMutableArray *)list
{
    startTime = CACurrentMediaTime();
    
    __block TLFUserStore *userStore = [TLFUserStore getInstance];
    __block TLFUser *currentUser = [userStore currentUser];
    
    __block UIView *indicatorView = [[NSBundle mainBundle] loadNibNamed:@"ActivityIndicatorView" owner:viewController.tableView.superview options:nil][0];
    indicatorView.center = CGPointMake(160, 176);
    [viewController.tableView addSubview:indicatorView];
    
    AFHTTPSessionManager *manager = [TLFNetworkHelper newSessionManager:userStore.authToken];
    
    void (^onSuccess)(NSURLSessionDataTask *operation, id responseObject);
    void (^onFailure)(NSURLSessionDataTask *operation, NSError *error);
    
    onSuccess = ^void(NSURLSessionDataTask *operation, id responseObject) {
        [TLFNetworkHelper sortUserResponse:responseObject byRole:role forList:list];
        
        dispatch_async(dispatch_get_main_queue(),^{
                       [indicatorView removeFromSuperview];
                       [viewController.tableView reloadData];
                       endTime = CACurrentMediaTime();
                       NSLog(@"Request users [%@] execution time: %f sec", role, (endTime - startTime));
                       
                       if ([role isEqualToString:@"business"]) {
                           [currentUser determineRedeemableBusinesses];
                           NSLog(@"Total redeemable businesses: %lu", (unsigned long)[currentUser.redeemableBusinesses count]);
                       }
        });
    };
    
    onFailure = ^void(NSURLSessionDataTask *operation, NSError *error) {
        NSLog(@"Request Failed: %@", [error localizedDescription]);
        
        // Show alert
        NSString *title = [NSString stringWithFormat:@"%@ list retrieval error", role];
        [TLFAlert alertForViewController:viewController forError:error withTitle:[title capitalizedString]];
    };
    
   [manager GET:[NSString stringWithFormat:@"v1/users/role/%@?id=%@", role, userStore.uid] parameters:nil success:onSuccess failure:onFailure];
}

+ (void)sortUserResponse:(id)responseObject byRole:(NSString *)role forList:(NSMutableArray *)list
{
    // Extract users based on role
    for (NSDictionary *obj in responseObject) {
        if ([obj[@"role"] isEqualToString:role]) {
            [list addObject:obj];
        }
    }
    
    NSLog(@"FIRST OBJECT: %@, %@", list[0][@"company_name"], list[0][@"role"]);
    
    // Sort alphabetically
    NSSortDescriptor *descriptor = [[NSSortDescriptor alloc]
                                    initWithKey:@"company_name"
                                    ascending:YES
                                    selector:@selector(localizedCaseInsensitiveCompare:)];
    
    [list sortUsingDescriptors:@[descriptor]];
    
    // Add to global store
    if ([role isEqualToString:@"business"]) {
        TLFBusinessStore *bStore = [TLFBusinessStore getInstance];
        bStore.businesses = list;
    }
    
    if ([role isEqualToString:@"cause"]) {
        TLFCauseStore *cStore = [TLFCauseStore getInstance];
        cStore.causes = list;
    }
    
    NSLog(@"Asynchronous Request Complete");
}

+(void)logoutUser:(NSString *)authToken successHandler:(void(^)(NSURLSessionDataTask* task, id responseObject))onSuccess failureHandler:(void(^)(NSURLSessionDataTask *task, NSError *error))onFailure
{
    AFHTTPSessionManager *manager = [TLFNetworkHelper newSessionManager:authToken];
    
    [manager DELETE:@"user/logout" parameters:nil success:onSuccess failure:onFailure];
}

@end
