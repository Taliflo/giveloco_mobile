//
//  TLFViewHelper.m
//  Taliflo
//
//  Created by NR-Mac on 1/1/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import "TranslateViewHelper.h"

@interface TranslateViewHelper ()

@property (nonatomic, weak) UIViewController *viewController;
@property CGPoint originalCenter;
@property (nonatomic) BOOL scroll;

@end

@implementation TranslateViewHelper

-(instancetype)initWithViewController:(UIViewController *)viewController
{
    self = [super init];
    if (self) {
        self.viewController = viewController;
        
        CGRect screenRect = [[UIScreen mainScreen] bounds];
        CGFloat screenHeight = screenRect.size.height;
        if (screenHeight == 480) {
            self.screenSize4s = YES;
        }
    }
    return self;
}

- (void)scrollViewUp:(int)dist
{
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.35f];
    self.originalCenter = self.viewController.view.center;
    if (dist != -1) {
        self.viewController.view.center = CGPointMake(self.originalCenter.x, self.originalCenter.y - dist);
    } else {
        self.viewController.view.center = CGPointMake(self.originalCenter.x, self.originalCenter.y - 24);
    }
    [UIView commitAnimations];
}

- (void)scrollViewBackToCenter
{
    if (self.scroll) {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.35f];
        self.viewController.view.center = self.originalCenter;
        [UIView commitAnimations];
    }
}

- (void)scrollViewUpForKeyboard:(UIView *)view
{
    int cuttOff = [[UIScreen mainScreen] bounds].size.height - 270;
    
    if (view.frame.origin.y > cuttOff) {
        self.scroll = YES;
        int scrollPoints = view.frame.origin.y + view.frame.size.height - cuttOff;
        
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.35f];
        [self scrollViewUp:scrollPoints];
        [UIView commitAnimations];
    } else {
        self.scroll = NO;
    }
}

@end
