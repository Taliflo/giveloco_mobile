//
//  TLFRestAPI.m
//  Taliflo
//
//  Created by NR-Mac on 1/14/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFRestAPI.h"


NSString *const base = @"http://api-dev.taliflo.com/v1/";

@implementation TLFRestAPI

+ (NSURL *)queryVouchers
{
    return [NSURL URLWithString:[base stringByAppendingString:@"vouchers"]];
}

+ (NSURL *)queryAllBusinesses
{
    return [NSURL URLWithString:[base stringByAppendingString:@"businesses"]];
}

+ (NSURL *)queryAllCauses
{
    return [NSURL URLWithString:[base stringByAppendingString:@"causes"]];
}

@end
