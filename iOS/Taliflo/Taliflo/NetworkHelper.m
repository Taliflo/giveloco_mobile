//
//  TLFRestAssistant.m
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "NetworkHelper.h"
#import "CauseStore.h"
#import "BusinessStore.h"
#import "UserStore.h"
#import "Alert.h"
#import "User.h"

@interface NetworkHelper () 

@end

@implementation NetworkHelper

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
    UserStore *userStore = [UserStore getInstance];
    AFHTTPSessionManager *manager = [NetworkHelper newSessionManager:userStore.authToken];
    
    [manager GET:[NSString stringWithFormat:@"v1/users/%@", userStore.uid] parameters:nil success:onSuccess failure:onFailure];
}

+ (void)requestUsers:(NSString *)role forTableViewController:(UITableViewController *)viewController backingList:(NSMutableArray *)list
{
    startTime = CACurrentMediaTime();
    
    __block UserStore *userStore = [UserStore getInstance];
    __block User *currentUser = [userStore currentUser];
    
    __block UIView *indicatorView = [[NSBundle mainBundle] loadNibNamed:@"ActivityIndicatorView" owner:viewController.tableView.superview options:nil][0];
    indicatorView.center = CGPointMake(160, 176);
    [viewController.tableView addSubview:indicatorView];
    
    AFHTTPSessionManager *manager = [NetworkHelper newSessionManager:userStore.authToken];
    
    void (^onSuccess)(NSURLSessionDataTask *operation, id responseObject);
    void (^onFailure)(NSURLSessionDataTask *operation, NSError *error);
    
    onSuccess = ^void(NSURLSessionDataTask *operation, id responseObject) {
        [NetworkHelper sortUserResponse:responseObject byRole:role forList:list];
        
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
        [Alert alertForViewController:viewController forError:error withTitle:[title capitalizedString]];
    };
    
   [manager GET:[NSString stringWithFormat:@"v1/users/role/%@?id=%@", role, userStore.uid] parameters:nil success:onSuccess failure:onFailure];
}

+ (void)sortUserResponse:(id)responseObject byRole:(NSString *)role forList:(NSMutableArray *)list
{
    // Extract users based on role
    for (NSDictionary *obj in responseObject) {
        NSNumber *isPublishedNum = obj[@"is_published"];
        BOOL isPublished = [isPublishedNum boolValue];
        if (isPublished && [obj[@"role"] isEqualToString:role]) {
            User *user = [[User alloc] initWithJSON:obj];
            [list addObject:user];
        }
    }
    
    NSLog(@"FIRST OBJECT: %@, %@", [list[0] companyName], [list[0] role]);
    
    if ([role isEqualToString:@"cause"]) {
        NSLog(@"Supporters: %@", [list[0] supporters]);
    }
    
    // Sort alphabetically
    NSSortDescriptor *descriptor = [[NSSortDescriptor alloc]
                                    initWithKey:@"companyName"
                                    ascending:YES
                                    selector:@selector(localizedCaseInsensitiveCompare:)];
    
    [list sortUsingDescriptors:@[descriptor]];
    
    // Add to global store
    if ([role isEqualToString:@"business"]) {
        BusinessStore *bStore = [BusinessStore getInstance];
        bStore.businesses = list;
    }
    
    if ([role isEqualToString:@"cause"]) {
        CauseStore *cStore = [CauseStore getInstance];
        cStore.causes = list;
    }
    
    NSLog(@"Asynchronous Request Complete");
}

+(void)logoutUser:(NSString *)authToken successHandler:(void(^)(NSURLSessionDataTask* task, id responseObject))onSuccess failureHandler:(void(^)(NSURLSessionDataTask *task, NSError *error))onFailure
{
    AFHTTPSessionManager *manager = [NetworkHelper newSessionManager:authToken];
    
    [manager DELETE:@"user/logout" parameters:nil success:onSuccess failure:onFailure];
}

@end
