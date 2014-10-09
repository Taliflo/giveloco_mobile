//
//  TLFAssociation.h
//  Taliflo
//
//  Created by NR-Mac on 1/7/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFAssociation : NSObject

@property (nonatomic, copy) NSNumber *connectionId, *fromUserId, *toUserId;
@property (nonatomic, strong) NSString *fromName, *toName;
@property (nonatomic, strong) NSArray *transactions;
@property (nonatomic, strong) NSDate *createdAt, *updatedAt;

- (instancetype)initWithJSON:(NSDictionary *)jsonObject;

@end
