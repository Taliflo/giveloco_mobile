//
//  TLFUserStore.h
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@class TLFUser;

@interface TLFUserStore : NSObject

@property (nonatomic, strong) TLFUser *currentUser;
@property (nonatomic, strong) NSString *authToken, *uid;

+ (instancetype)getInstance;

@end
