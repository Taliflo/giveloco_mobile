//
//  TLFUserStore.h
//  Taliflo
//
//  Created by NR-Mac on 1/27/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@class User;

@interface UserStore : NSObject

@property (nonatomic, strong) User *currentUser;
@property (nonatomic, copy) NSString *authToken, *uid;

+ (instancetype)getInstance;

- (void)setLoggedInCredentials:(NSDictionary *)credentials;

@end
