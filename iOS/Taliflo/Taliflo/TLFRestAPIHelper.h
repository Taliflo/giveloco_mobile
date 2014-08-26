//
//  TLFRestAPIHelper.h
//  Taliflo
//
//  Created by NR-Mac on 1/25/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFRestAPIHelper : NSObject

+ (instancetype)getInstance;
- (NSURL *)queryAllUsers;
- (NSURL *)queryAllBusinesses;
- (NSURL *)queryAllCauses;

@end
