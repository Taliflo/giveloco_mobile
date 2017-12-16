//
//  TLFRestAssistant.h
//  taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AFNetworking/AFHTTPSessionManager.h>
#import <AFNetworking/AFHTTPRequestOperation.h>

@class CauseStore, BusinessStore;

@interface NetworkHelper : NSObject

+ (void)requestUsers:(NSString *)role forTableViewController:(UITableViewController *)viewController backingList:(NSMutableArray *)list;

+ (void)requestUser:(NSString *)uid successHandler:(void (^)(NSURLSessionDataTask *operation, id responseObject))onSuccess failureHandler:(void (^)(NSURLSessionDataTask *operation, NSError *error))onFailure;

+(void)logoutUser:(NSString *)authToken successHandler:(void(^)(NSURLSessionDataTask* task, id responseObject))onSuccess failureHandler:(void(^)(NSURLSessionDataTask *task, NSError *error))onFailure;

+ (AFHTTPSessionManager *)newSessionManager:(NSString *)authToken;


@end
