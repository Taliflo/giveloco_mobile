//
//  TLFTransaction.m
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TLFTransaction.h"

@interface TLFTransaction ()

@end

@implementation TLFTransaction

static NSDateFormatter *_inFormatter;
static NSDateFormatter *_outFormatter;

- (instancetype)initWithJSON:(NSDictionary *)jsonObject
{
    self = [super init];
    
    if (_inFormatter == nil) {
        _inFormatter = [[NSDateFormatter alloc] init];
        [_inFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSSZ"];
    }
    
    if (_outFormatter == nil) {
        _outFormatter = [[NSDateFormatter alloc] init];
        _outFormatter.dateStyle = NSDateFormatterShortStyle;
        _outFormatter.timeStyle = NSDateFormatterNoStyle;
    }
    
    if (self) {
        _ID = jsonObject[@"id"];
        _transID = jsonObject[@"trans_id"];
        _stripeID = jsonObject[@"stripe_id"];
        _transType = jsonObject[@"trans_type"];
        _fromUserID = jsonObject[@"from_user_id"];
        _toUserID = jsonObject[@"to_user_id"];
        _fromName = jsonObject[@"from_name"];
        _toName = jsonObject[@"to_name"];
        _fromUserRole = jsonObject[@"from_user_role"];
        _toUserRole = jsonObject[@"to_user_role"];
        _amount = jsonObject[@"amount"];
        _status = jsonObject[@"status"];
        
        _createdAt = [_inFormatter dateFromString:jsonObject[@"created_at"]];
        _updatedAt = [_inFormatter dateFromString:jsonObject[@"updated_at"]];
    }
    

    
    return self;
}

- (NSString *)formatCancelledAt
{
    return [_outFormatter stringFromDate:_cancelledAt];
}

- (NSString *)formatCompletedAt
{
    return [_outFormatter stringFromDate:_completedAt];
}

- (NSString *)formatCreatedAt
{
    return [_outFormatter stringFromDate:_createdAt];
}

- (NSString *)formateUpdatedAt
{
    return [_outFormatter stringFromDate:_updatedAt];
}

@end
