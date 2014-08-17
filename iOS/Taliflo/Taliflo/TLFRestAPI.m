//
//  TLFRestAPI.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFRestAPI.h"
#import "AFNetworking.h"


//NSString *const base = @"http://api-dev.taliflo.com/v1/";
 NSString *const base = @"http://sheltered-wave-9353.herokuapp.com/";

@implementation TLFRestAPI

+ (NSURL *)queryAllUsers
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users"]];
}

+ (NSURL *)queryAllBusinesses
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users?role=business"]];
}

+ (NSURL *)queryAllCauses
{
    return [NSURL URLWithString:[base stringByAppendingString:@"users?role=cause"]];
}

@end
